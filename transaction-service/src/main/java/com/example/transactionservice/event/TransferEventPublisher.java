package com.example.transactionservice.event;

import com.example.transactionservice.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferEventPublisher {

  private final RabbitTemplate template;

  @Value("${rabbit.queue.name}")
  private String queueName;

  @Value("${rabbit.exchange.name}")
  private String exchangeName;

  @Value("${rabbit.routing.key}")
  private String routingKey;

  public void publishPurchaseMadeEvent(Transaction transaction) {
    template.convertAndSend(exchangeName, routingKey, transaction);
    System.out.println("Published transaction event");
  }
}
