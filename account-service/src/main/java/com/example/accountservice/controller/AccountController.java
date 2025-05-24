package com.example.accountservice.controller;

import com.example.accountservice.dto.TransactionDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Flux<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/{accountId}")
    public Mono<Account> getById(@PathVariable Long accountId) {
        return accountService.getById(accountId);
    }

    @PostMapping("/")
    public Mono<Account> createAccount(@RequestBody Account newAccount) {
        return accountService.createAccount(newAccount);
    }

    @PutMapping("/{accountId}")
    public Mono<Account> update(@PathVariable Long accountId, @RequestBody Account changeAccount) {
        return accountService.updateAccount(accountId, changeAccount);
    }

    @PutMapping("/{accountId}/debit")
    public Mono<Account> debit(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        return accountService.debit(accountId, amount);
    }
    @PutMapping("/{accountId}/credit")
    public Mono<Account> credit(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        return accountService.credit(accountId, amount);
    }

    @GetMapping("/GetMovements/{accountId}")
    public List<TransactionDTO> obtenerMovimientos(@PathVariable Long accountId) {
        return accountService.obtenerMovimientos(accountId);
    }

}
