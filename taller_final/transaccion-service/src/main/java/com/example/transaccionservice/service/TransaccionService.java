package com.example.transaccionservice.service;

import com.example.transaccionservice.dto.CuentaDTO;
import com.example.transaccionservice.dto.TransaccionDTO;
import com.example.transaccionservice.event.EventoTransferencia;
import com.example.transaccionservice.model.Transaccion;
import com.example.transaccionservice.repository.ITransaccionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransaccionService {

    private final ITransaccionRepository iTransaccionRepository;
    private final CuentaService cuentaservice;
    private final EventoTransferencia eventoTransferencia;

    public TransaccionService(ITransaccionRepository itransaccionRepository,
                              CuentaService cuentaservice,
                              EventoTransferencia eventoTransferencia) {
        this.iTransaccionRepository = itransaccionRepository;
        this.cuentaservice = cuentaservice;
        this.eventoTransferencia = eventoTransferencia;
    }

    public Flux<Transaccion> getAll() {
        return iTransaccionRepository.findAll();
    }

    public Mono<Transaccion> create(Transaccion transaccion) {
        transaccion.setNumeroRastreo(null);
        return iTransaccionRepository.save(transaccion);
    }

    public Mono<Void> makeTransaccion(TransaccionDTO transfer) {
        BigDecimal monto = transfer.getMonto();

        return cuentaservice.getCuenta(transfer.getCuentaOrigen())
                .zipWith(cuentaservice.getCuenta(transfer.getCuentaDestino()))
                .flatMap(tuple -> {
                    CuentaDTO ctaorigen = tuple.getT1();
                    CuentaDTO ctadestino = tuple.getT2();

                    if (ctaorigen.getSaldo().compareTo(monto) < 0) {
                        return Mono.error(new RuntimeException("Saldo insuficiente para efectuar la transaccion"));
                    }

                    ctaorigen.setSaldo(ctaorigen.getSaldo().subtract(monto));
                    Transaccion debito = new Transaccion(null,
                            ctaorigen.getTipoCuenta(),
                            ctaorigen.getBancoId(),
                            BigDecimal.valueOf(ctaorigen.getNumeroCuenta()),
                            "D",
                            monto,
                            LocalDateTime.now()
                    );

                    if (ctaorigen.getBancoId().equals(ctadestino.getBancoId())) {
                        ctadestino.setSaldo(ctadestino.getSaldo().add(monto));
                        Transaccion credito = new Transaccion(null,
                                ctadestino.getTipoCuenta(),
                                ctadestino.getBancoId(),
                                BigDecimal.valueOf(ctadestino.getNumeroCuenta()),
                                "C",
                                monto,
                                LocalDateTime.now()
                        );
                        System.out.println("Cuenta recibida: " + ctaorigen.getNumeroCuenta());
                        System.out.println("Cuenta destino: " + ctadestino.getNumeroCuenta());

                        return Mono.when(
                                cuentaservice.updateCuenta(ctaorigen),
                                iTransaccionRepository.save(debito),
                                cuentaservice.updateCuenta(ctadestino),
                                iTransaccionRepository.save(credito)
                        ).then();
                    } else {
                        Transaccion credito = new Transaccion(ctadestino.getNumeroCuenta(),
                                ctadestino.getTipoCuenta(),
                                ctadestino.getBancoId(),
                                BigDecimal.valueOf(ctadestino.getNumeroCuenta()),
                                "C",
                                monto,
                                LocalDateTime.now()
                        );

                        return Mono.when(
                                cuentaservice.updateCuenta(ctaorigen),
                                iTransaccionRepository.save(debito),
                                Mono.fromRunnable(() -> eventoTransferencia.publicacionEncolada(credito))
                        ).then();
                    }
                });
    }
}
