package com.axceldev.transferservice.service;

import com.axceldev.transferservice.dto.TransactionMessageDto;
import com.axceldev.transferservice.dto.UpdateBalanceRequest;
import com.axceldev.transferservice.model.Transaction;
import com.axceldev.transferservice.repository.ITransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TransferService {
    private final ITransferRepository transferRepository;
    private final WebClient webClient;
    private static final double TAX_RATE = 0.01;

    @RabbitListener(queues = "deposit-inter-bank-queue")
    public void receiveDeposit(TransactionMessageDto deposit) {

        Transaction transactionWithTax = buildTransaction(deposit);

        transferRepository.save(transactionWithTax)
                .subscribe(
                        transaction -> {
                            System.out.println("Transaction saved: " + transaction);
                            webClient.post()
                                    .uri("/api/accounts/update-balance")
                                    .bodyValue(new UpdateBalanceRequest(
                                            transaction.getAccountNumber(),
                                            transaction.getAmount(), transaction.getTransactionType()
                                    ))
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .subscribe(response -> System.out.println("Balance updated: " + response));
                        },
                        error -> System.err.println("Error saving transaction: " + error.getMessage()
                ));
    }

    private Transaction buildTransaction(TransactionMessageDto deposit) {
        double tax = deposit.getAmount() * TAX_RATE;
        double finalAmount = deposit.getAmount() - tax;

        return Transaction.builder()
                .accountNumber(deposit.getAccountNumber())
                .transactionType(deposit.getTransactionType())
                .amount(finalAmount)
                .currency(deposit.getCurrency())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
