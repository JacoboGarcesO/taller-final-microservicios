package com.example.cuentasservice.service;

import com.example.cuentasservice.grpc.Consumidor;
import com.example.cuentasservice.dto.CuentaDTO;
import com.example.cuentasservice.dto.TransaccionDTO;
import com.example.cuentasservice.model.Cuenta;
import com.example.cuentasservice.repository.ICuentaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CuentaService {

    private final ICuentaRepository cuentaRepository;
    private final GetBanco getBanco;
    private final Consumidor consumidor;

    public CuentaService(ICuentaRepository cuentaRepository, GetBanco getBanco, Consumidor consumidor) {
        this.cuentaRepository = cuentaRepository;
        this.getBanco = getBanco;
        this.consumidor = consumidor;
    }

    public Flux<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    public Mono<Cuenta> getById(Long cuenta) {
        return cuentaRepository.findById(cuenta)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")));
    }

    public Mono<Cuenta> create(CuentaDTO cuentaDTO) {
        return getBanco.getBanco(cuentaDTO.getBancoId())
                .flatMap(banco -> {
                    Cuenta cuenta = new Cuenta(
                            null,
                            cuentaDTO.getBancoId(),
                            cuentaDTO.getTipoCuenta(),
                            cuentaDTO.getSaldo(),
                            cuentaDTO.getSaldoSobregiro(),
                            cuentaDTO.getEstado()
                    );
                    return cuentaRepository.save(cuenta);
                });
    }

    public Mono<Cuenta> update(Cuenta cuenta) {
        return cuentaRepository.findById(cuenta.getNumeroCuenta())
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada!!")))
                .flatMap(existing -> cuentaRepository.save(cuenta));
    }
    public Flux<TransaccionDTO> getMovements(Long numeroCuenta) {
        return Flux.fromIterable(consumidor.getTransacciones(numeroCuenta))
                .map(transaccion -> new TransaccionDTO(
                        transaccion.getNumeroRastreo(),
                        transaccion.getTipoCuenta(),
                        transaccion.getBanco(),
                        BigDecimal.valueOf(transaccion.getCuenta()),
                        transaccion.getTipoTransaccion(),
                        BigDecimal.valueOf(transaccion.getMonto()),
                        LocalDateTime.parse(transaccion.getFecha())
                ));
    }
}
