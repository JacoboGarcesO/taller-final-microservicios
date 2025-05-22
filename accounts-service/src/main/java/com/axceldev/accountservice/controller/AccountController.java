package com.axceldev.accountservice.controller;

import com.axceldev.accountservice.dto.AccountNumberBankIdResponse;
import com.axceldev.accountservice.dto.CreateAccountRequest;
import com.axceldev.accountservice.dto.HasSufficientFundsRequest;
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
    public ResponseEntity<Flux<AccountNumberBankIdResponse>> getAccountsByNumbers(@RequestBody List<String> accountNumbers) {
        return ResponseEntity.ok(accountService.findByAccountNumbers(accountNumbers).doOnError(throwable -> {
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

}
