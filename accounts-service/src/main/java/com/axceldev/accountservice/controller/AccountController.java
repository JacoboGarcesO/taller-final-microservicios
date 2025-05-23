package com.axceldev.accountservice.controller;

import com.axceldev.accountservice.dto.*;
import com.axceldev.accountservice.model.Account;
import com.axceldev.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<Mono<Account>> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request)
                        .doOnError(throwable -> {
                            throw new RuntimeException("Error creating account", throwable);
                        }).onErrorResume(throwable -> Mono.empty())
        );
    }

    @GetMapping("/batch")
    public ResponseEntity<Flux<AccountNumberBankIdResponse>> getAccountsByNumbers(
            @RequestParam String sourceAccountNumber,
            @RequestParam String destinationAccountNumber) {
        List<String> accountNumberList = List.of(sourceAccountNumber,destinationAccountNumber);
        return ResponseEntity.ok(accountService.findByAccountNumbers(accountNumberList)
                        .doOnError(throwable -> {
            throw new RuntimeException("Error fetching accounts", throwable);
        }).onErrorResume(throwable -> Flux.empty())
        );
    }

    @GetMapping("/has-sufficient-funds")
    public Mono<Boolean> hasSufficientFunds(
            @RequestParam String accountNumber,
            @RequestParam Double amount) {
        HasSufficientFundsRequest request = new HasSufficientFundsRequest(accountNumber, amount);
        return accountService.hasSufficientFunds(request)
                .doOnError(throwable -> {;
                    throw new RuntimeException("Error checking sufficient funds", throwable);
                })
                .onErrorResume(throwable -> Mono.just(false));
    }

    @GetMapping("/transaction-history")
    public Mono<List<TransactionsHistoryResponse>> getTransactionHistory(@RequestParam String accountNumber) {
        return accountService.getTransactionHistory(accountNumber)
                .doOnError(throwable -> {
                    throw new RuntimeException("Error fetching transaction history", throwable);
                })
                .onErrorResume(throwable -> Mono.empty());
    }

    @PostMapping("/update-balance")
    public Mono<Boolean> updateBalance(@RequestBody UpdateBalanceRequest request) {
        return accountService.updateBalance(request)
                .doOnError(throwable -> {
                    throw new RuntimeException("Error updating balance", throwable);
                })
                .onErrorResume(throwable -> Mono.empty());
    }



}
