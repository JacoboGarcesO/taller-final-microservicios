package com.example.transaction.service;

import com.example.transaction.dto.TransferRequestDTO;
import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String QUEUE_NAME = "interbank.transfer.queue";

    public void processTransfer(TransferRequestDTO dto) {
        if (!hasSufficientFunds(dto.getSourceAccountId(), dto.getAmount())) {
            throw new RuntimeException("Insufficient funds");
        }

        Transaction withdrawal = new Transaction(
                dto.getSourceAccountId(),
                dto.getAmount().negate(),
                LocalDateTime.now(),
                "WITHDRAWAL",
                dto.getSourceBankId()
        );
        transactionRepository.save(withdrawal);

        if (dto.getSourceBankId().equals(dto.getDestinationBankId())) {
            Transaction deposit = new Transaction(
                    dto.getDestinationAccountId(),
                    dto.getAmount(),
                    LocalDateTime.now(),
                    "DEPOSIT",
                    dto.getDestinationBankId()
            );
            transactionRepository.save(deposit);
        } else {
            rabbitTemplate.convertAndSend(QUEUE_NAME, dto);
        }
    }

    private boolean hasSufficientFunds(String accountId, BigDecimal amount) {
        return transactionRepository.findByAccountId(accountId).stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .compareTo(amount) >= 0;
    }
}