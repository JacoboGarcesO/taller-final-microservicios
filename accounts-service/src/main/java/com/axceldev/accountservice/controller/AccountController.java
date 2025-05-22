package com.axceldev.accountservice.controller;

import com.axceldev.accountservice.dto.CreateAccountRequest;
import com.axceldev.accountservice.model.Account;
import com.axceldev.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

}
