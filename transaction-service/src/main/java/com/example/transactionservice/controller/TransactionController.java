package com.example.transactionservice.controller;

import com.example.transactionservice.model.TransferRequest;
import com.example.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public Mono<Void> transfer(@RequestBody TransferRequest transferRequest) {
        return transactionService.processTransfer(transferRequest);
    }

    @PutMapping("/{accountId}/credit")
    public Mono<Void> registrarDeposito(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        return transactionService.registrarDeposito(accountId, amount);
    }
}
