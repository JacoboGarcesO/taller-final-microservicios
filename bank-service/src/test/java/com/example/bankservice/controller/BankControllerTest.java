package com.example.bankservice.controller;

import com.example.bankservice.model.Bank;
import com.example.bankservice.service.BankService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class BankControllerTest {

    private final BankService bankService;
    private final WebTestClient webTestClient;

    private static final Bank BANK = new Bank(1L, "Bancolombia", "The best");

    public BankControllerTest() {
        bankService = Mockito.mock(BankService.class);
        webTestClient = WebTestClient.bindToController(new BankController(bankService)).build();
    }

    @Test
    void getAll_ShouldReturnAllBanks() {

        Mockito.when(bankService.getAll())
                .thenReturn(Flux.just(BANK));

        webTestClient.get()
                .uri("/api/banks")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Bank.class)
                .hasSize(1)
                .value(banks -> {
                    Assertions.assertNotNull(banks);
                    Assertions.assertEquals("Bancolombia", banks.get(0).getName());
                    Assertions.assertEquals("The best", banks.get(0).getDescription());
                });

        Mockito.verify(bankService).getAll();
    }

    @Test
    void getById_ShouldReturnBankById() {

        Mockito.when(bankService.getById(Mockito.anyLong()))
                .thenReturn(Mono.just(BANK));

        webTestClient.get()
                .uri("/api/banks/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bank.class)
                .value(bank -> {
                    Assertions.assertNotNull(bank);
                    Assertions.assertEquals("Bancolombia", bank.getName());
                    Assertions.assertEquals("The best", bank.getDescription());
                });

        Mockito.verify(bankService).getById(Mockito.anyLong());
    }

    @Test
    void create_ShouldSaveAndReturnBank() {

        Mockito.when(bankService.create(Mockito.any(Bank.class)))
                .thenReturn(Mono.just(BANK));

        webTestClient.post()
                .uri("/api/banks")
                .bodyValue(BANK)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bank.class)
                .value(bank -> {
                    Assertions.assertNotNull(bank);
                    Assertions.assertEquals("Bancolombia", bank.getName());
                    Assertions.assertEquals("The best", bank.getDescription());
                });

        Mockito.verify(bankService).create(Mockito.any(Bank.class));
    }

    @Test
    void update_ShouldUpdateAndReturnBank() {

        Mockito.when(bankService.update(Mockito.any(Bank.class)))
                .thenReturn(Mono.just(BANK));

        webTestClient.put()
                .uri("/api/banks")
                .bodyValue(BANK)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bank.class)
                .value(bank -> {
                    Assertions.assertNotNull(bank);
                    Assertions.assertEquals("Bancolombia", bank.getName());
                    Assertions.assertEquals("The best", bank.getDescription());
                });

        Mockito.verify(bankService).update(Mockito.any(Bank.class));
    }
}
