package com.example.transferenciaservice.mensajeria;

import com.example.transferenciaservice.model.Transferencia;
import com.example.transferenciaservice.service.TransferenciaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TransferenciasListener {
    public TransferenciasListener(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    private final TransferenciaService transferenciaService;

    @RabbitListener(queues = "${rabbit.queue.name}")
    public void listen(Transferencia transaction) {
        transferenciaService.saveTransferencia(transaction)
                .doOnSuccess(unused -> System.out.println("Transaccion exitosa"))
                .doOnError(error -> System.err.println("Error procesando la transacción: " + error.getMessage()))
                .subscribe();
    }
}
