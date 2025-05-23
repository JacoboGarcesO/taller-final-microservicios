package com.axceldev.accountservice.controller;

import com.axceldev.accountservice.dto.CreateAccountRequest;
import com.axceldev.accountservice.model.Account;
import com.axceldev.accountservice.model.AccountType;
import com.axceldev.accountservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class AccountControllerTest {

    private final AccountService accountService = Mockito.mock(AccountService.class);
    private final AccountController accountController = new AccountController(accountService);

    @Test
    void createAccount_success() {
        CreateAccountRequest request = new CreateAccountRequest("12345", null, null, 1L);
        Account account = Account.builder()
                .accountNumber("12345")
                .accountType(AccountType.SAVINGS)
                .balance(1000.0)
                .accountId(1L)
                .bankId(1L)
                .build();

        Mockito.when(accountService.createAccount(any(CreateAccountRequest.class))).thenReturn(Mono.just(account));

        ResponseEntity<Mono<Account>> responseEntity = accountController.createAccount(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        StepVerifier.create(responseEntity.getBody())
                .assertNext(createdAccount -> assertEquals("12345", createdAccount.getAccountNumber()))
                .verifyComplete();
    }

    @Test
    void createAccount_failure() {
        CreateAccountRequest request = new CreateAccountRequest("12345", null, null, 1L);

        Mockito.when(accountService.createAccount(any(CreateAccountRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("Service failure")));

        ResponseEntity<Mono<Account>> responseEntity = accountController.createAccount(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        StepVerifier.create(responseEntity.getBody())
                .expectComplete()
                .verify();
    }
}