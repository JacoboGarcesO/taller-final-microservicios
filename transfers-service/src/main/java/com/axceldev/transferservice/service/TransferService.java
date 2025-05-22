package com.axceldev.transferservice.service;

import com.axceldev.transferservice.dto.TransactionMessageDto;
import com.axceldev.transferservice.model.Transaction;
import com.axceldev.transferservice.repository.ITransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TransferService {
    private final ITransferRepository transferRepository;
    private static final double TAX_RATE = 0.01;

    @RabbitListener(queues = "deposit-inter-bank-queue")
    public void receiveDeposit(TransactionMessageDto deposit) {

        Transaction transactionWithTax = buildTransaction(deposit);

        transferRepository.save(transactionWithTax).subscribe();
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
