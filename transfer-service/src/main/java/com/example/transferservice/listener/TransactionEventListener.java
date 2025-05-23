package com.example.transferservice.listener;

import com.example.transferservice.model.Transaction;
import com.example.transferservice.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionEventListener {

  private final TransferService transferService;

  @RabbitListener(queues = "${rabbit.queue.name}")
  public void listen(Transaction transaction) {
    transferService.saveTransfer(transaction)
            .doOnSuccess(unused -> System.out.println("Transaction processed successfully"))
            .doOnError(error -> System.err.println("Error processing transaction: " + error.getMessage()))
            .subscribe();
  }
}
