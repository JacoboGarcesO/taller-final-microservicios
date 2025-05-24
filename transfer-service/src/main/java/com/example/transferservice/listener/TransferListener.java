package com.example.transferservice.listener;

import com.example.transferservice.dto.TransferRequest;
import com.example.transferservice.service.TransferService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TransferListener {

    private final TransferService service;

    public TransferListener(TransferService service) {
        this.service = service;
    }

    @RabbitListener(queues = "${rabbit.queue.name}")
    public void recibir(TransferRequest request) {
        System.out.println("📥 Transferencia recibida: " + request);
        service.registrarDepositoConImpuesto(request)
                .subscribe(); // ejecución reactiva
    }
}

