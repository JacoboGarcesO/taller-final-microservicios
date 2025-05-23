package com.axceldev.transactionservice.controller;

import com.axceldev.transactionservice.dto.CreateTransactionRequest;
import com.axceldev.transactionservice.model.Transaction;
import com.axceldev.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping()
    public Mono<List<Transaction>> createTransaction(@RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request)
                .onErrorResume(throwable -> Mono.error(new RuntimeException("Error creating transaction", throwable)));
    }
}
