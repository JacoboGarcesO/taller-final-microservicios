package com.example.transactionservice.event;

import com.example.transactionservice.model.TransferRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
public class TransferEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.queue.name}")
    private String queueName;

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    public TransferEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Mono<Void> sendTransferToQueue(TransferRequest transferRequest) {
        return Mono.fromRunnable(() -> {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, transferRequest);
            System.out.println("Transferencia interbancaria enviada a cola: " + transferRequest);
        });
    }
}

