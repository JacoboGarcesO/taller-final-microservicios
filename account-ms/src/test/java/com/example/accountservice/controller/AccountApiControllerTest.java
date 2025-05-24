package com.example.accountservice.controller;

import com.example.accountservice.dto.TransactionDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountApiControllerTest {

    private AccountManagementService mockService;
    private WebTestClient client;

    private static final Account SAMPLE_ACCOUNT = new Account(1L, "AHORROS", new BigDecimal("100000.00"), "active", 5L);
    private static final TransactionDTO SAMPLE_TX = new TransactionDTO(
            1L,
            "DEPOSITO",
            4L,
            1L,
            new BigDecimal("100000.00"),
            "Test deposit",
            LocalDateTime.of(2024, 5, 21, 10, 0)
    );

    @BeforeEach
    void setup() {
        mockService = Mockito.mock(AccountManagementService.class);
        client = WebTestClient.bindToController(new AccountController(mockService)).build();
    }

    @Test
    void shouldFetchAllAccounts() {
        Mockito.when(mockService.getAll())
                .thenReturn(Flux.just(SAMPLE_ACCOUNT));

        client.get()
                .uri("/api/accounts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Account.class)
                .value(accounts -> {
                    assertFalse(accounts.isEmpty());
                    assertEquals(1, accounts.size());
                    assertEquals(0, accounts.get(0).getBalance().compareTo(new BigDecimal("100000.00")));
                });

        Mockito.verify(mockService).getAll();
    }

    @Test
    void shouldReturnAccountById() {
        Mockito.when(mockService.getById(1L))
                .thenReturn(Mono.just(SAMPLE_ACCOUNT));

        client.get()
                .uri("/api/accounts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(acc -> {
                    assertNotNull(acc);
                    assertEquals(1L, acc.getAccountNumber());
                    assertEquals("AHORROS", acc.getAccountType());

                });

        Mockito.verify(mockService).getById(1L);
    }

    @Test
    void shouldCreateNewAccount() {
        Mockito.when(mockService.create(Mockito.any()))
                .thenReturn(Mono.just(SAMPLE_ACCOUNT));

        client.post()
                .uri("/api/accounts")
                .bodyValue(SAMPLE_ACCOUNT)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(result -> {
                    assertNotNull(result);
                    assertEquals("active", result.getStatus());
                });

        Mockito.verify(mockService).create(Mockito.any());
    }

    @Test
    void shouldUpdateAccountSuccessfully() {
        Mockito.when(mockService.update(Mockito.any()))
                .thenReturn(Mono.just(SAMPLE_ACCOUNT));

        client.put()
                .uri("/api/accounts")
                .bodyValue(SAMPLE_ACCOUNT)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .value(updated -> {
                    assertNotNull(updated);
                });

        Mockito.verify(mockService).update(Mockito.any());
    }

    @Test
    void shouldReturnAccountMovements() {
        Mockito.when(mockService.getMovements(Mockito.eq(1L)))
                .thenReturn(Flux.just(SAMPLE_TX));

        client.get()
                .uri("/api/accounts/movements/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionDTO.class)
                .value(moves -> {
                    assertNotNull(moves);
                    assertTrue(moves.get(0).getType().startsWith("DEP"));
                });

        Mockito.verify(mockService).getMovements(1L);
    }
}
