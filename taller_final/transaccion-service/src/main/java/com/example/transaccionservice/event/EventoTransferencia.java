package com.example.transaccionservice.event;

import com.example.transaccionservice.model.Transaccion;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventoTransferencia {

    private final RabbitTemplate template;

    @Value("${rabbit.queue.name}")
    private String queueName;

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    public EventoTransferencia(RabbitTemplate template) {
        this.template = template;
    }

    public void publicacionEncolada(Transaccion transaccion) {
        template.convertAndSend(exchangeName, routingKey, transaccion);
        System.out.println("Transacción enviada correctamente a la cola");
    }
}
