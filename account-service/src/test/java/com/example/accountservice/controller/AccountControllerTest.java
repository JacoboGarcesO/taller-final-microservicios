package com.example.accountservice.controller;

import com.example.accountservice.dto.TransactionDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class AccountControllerTest {

    private final AccountService accountService;
    private final WebTestClient webTestClient;

    private static final Account ACCOUNT = new Account(1L, "AHORROS", new BigDecimal("100000.00"), "active", 5L);
    private static final TransactionDTO TRANSACTION_DTO = new TransactionDTO(
            1L,
            "DEPOSITO",
            4L,
            1L,
            new BigDecimal("100000.00"),
            "Test deposit",
            LocalDateTime.of(2024, 5, 21, 10, 0)
    );

    public AccountControllerTest() {
        accountService = Mockito.mock(AccountService.class);
        webTestClient = WebTestClient.bindToController(new AccountController(accountService)).build();
    }

    @Test
    void getAll_ShouldReturnAllAccounts() {

        Mockito.when(accountService.getAll())
                .thenReturn(Flux.just(ACCOUNT));

        webTestClient.get()
                .uri("/api/accounts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Account.class)
                .hasSize(1)
                .value(accounts -> {
                    Assertions.assertNotNull(accounts);
                    Assertions.assertEquals(1L, accounts.get(0).getAccountNumber());
                    Assertions.assertEquals(new BigDecimal("100000.00"), accounts.get(0).getBalance());
                });

        Mockito.verify(accountService).getAll();
    }

    @Test
    void getById_ShouldReturnAccount() {

        Mockito.when(accountService.getById(1L))
                .thenReturn(Mono.just(ACCOUNT));

        webTestClient.get()
                .uri("/api/accounts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(account -> {
                    Assertions.assertNotNull(account);
                    Assertions.assertEquals(1L, account.getAccountNumber());
                    Assertions.assertEquals(new BigDecimal("100000.00"), account.getBalance());
                });

        Mockito.verify(accountService).getById(1L);
    }

    @Test
    void create_ShouldSaveAndReturnAccount() {

        Mockito.when(accountService.create(Mockito.any(Account.class)))
                .thenReturn(Mono.just(ACCOUNT));

        webTestClient.post()
                .uri("/api/accounts")
                .bodyValue(ACCOUNT)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(account -> {
                    Assertions.assertNotNull(account);
                    Assertions.assertEquals(1L, account.getAccountNumber());
                    Assertions.assertEquals(new BigDecimal("100000.00"), account.getBalance());
                });

        Mockito.verify(accountService).create(Mockito.any(Account.class));
    }

    @Test
    void update_ShouldUpdateAndReturnAccount() {

        Mockito.when(accountService.update(Mockito.any(Account.class)))
                .thenReturn(Mono.just(ACCOUNT));

        webTestClient.put()
                .uri("/api/accounts")
                .bodyValue(ACCOUNT)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(account -> {
                    Assertions.assertNotNull(account);
                    Assertions.assertEquals(1L, account.getAccountNumber());
                    Assertions.assertEquals(new BigDecimal("100000.00"), account.getBalance());
                });

        Mockito.verify(accountService).update(Mockito.any(Account.class));
    }

    @Test
    void getMovements_ShouldReturnTransactionList() {

        Mockito.when(accountService.getMovements(Mockito.anyLong()))
                .thenReturn(Flux.just(TRANSACTION_DTO));

        webTestClient.get()
                .uri("/api/accounts/movements/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionDTO.class)
                .hasSize(1)
                .value(transactions -> {
                    Assertions.assertNotNull(transactions);
                    Assertions.assertEquals("DEPOSITO", transactions.get(0).getType());
                    Assertions.assertEquals(new BigDecimal("100000.00"), transactions.get(0).getAmount());
                });

        Mockito.verify(accountService).getMovements(Mockito.anyLong());
    }
}
