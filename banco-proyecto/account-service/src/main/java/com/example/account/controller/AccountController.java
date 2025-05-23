package com.example.account.controller;

import com.example.account.model.Account;
import com.example.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping
    public Mono<ResponseEntity<Account>> create(@RequestBody Account account) {
        return service.createAccount(account)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(null)));
    }

    @GetMapping
    public Flux<Account> getAll() {
        return service.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Account>> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Account>> update(@PathVariable String id, @RequestBody Account account) {
        return service.updateAccount(id, account)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}