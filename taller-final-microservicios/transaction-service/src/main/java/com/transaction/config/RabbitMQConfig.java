package com.transaction.service;

import com.transaction.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

  private final RabbitTemplate rabbitTemplate;

  public MessagingService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendInterbankTransaction(Transaction transaction) {
    rabbitTemplate.convertAndSend("interbank-queue", transaction);
  }

  @Bean
  public Queue queue() {
    return new Queue(QUEUE, false);
  }
}
}