package com.axceldev.transactionservice.service;

import com.axceldev.transactionservice.repository.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final ITransactionRepository transactionRepository;
}
