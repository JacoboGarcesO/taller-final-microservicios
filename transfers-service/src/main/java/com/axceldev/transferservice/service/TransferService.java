package com.axceldev.transferservice.service;

import com.axceldev.transferservice.model.Transaction;
import com.axceldev.transferservice.model.TransactionType;
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
    public void receiveDeposit(Object deposit) {

        if (!(deposit instanceof Transaction)) {
            throw new IllegalArgumentException("Invalid deposit object");
        }

        Transaction depositTrx = (Transaction) deposit;
        double tax = depositTrx.getAmount() * TAX_RATE;
        double finalAmount = depositTrx.getAmount() - tax;

        Transaction transactionWithTax = Transaction.builder()
                .accountNumber(depositTrx.getAccountNumber())
                .transactionType(TransactionType.DEPOSIT)
                .amount(finalAmount)
                .currency(depositTrx.getCurrency())
                .createdAt(LocalDateTime.now())
                .build();

        transferRepository.save(transactionWithTax).subscribe();
    }
}
