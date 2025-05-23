package com.example.account.service;

import com.example.account.dto.AccountDTO;
import com.example.account.dto.TransactionDTO;
import com.example.account.model.Account;
import com.example.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public Mono<ResponseEntity<Account>> createAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO)
                .map(account -> ResponseEntity.ok(account));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Account>> getAccountById(@PathVariable String id) {
        return accountService.getAccountById(id)
                .map(account -> ResponseEntity.ok(account))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Account>> updateAccount(@PathVariable String id, @RequestBody AccountDTO accountDTO) {
        return accountService.updateAccount(id, accountDTO)
                .map(account -> ResponseEntity.ok(account))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable String id) {
        return accountService.deleteAccount(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}