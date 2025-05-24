package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Flux<Account> getAll(){
        return accountService.getAll();
    }

    @PostMapping
    public Mono<Account> newAccount(@RequestBody AccountDTO newAccount){
        return accountService.createAccount(newAccount);
    }
}
