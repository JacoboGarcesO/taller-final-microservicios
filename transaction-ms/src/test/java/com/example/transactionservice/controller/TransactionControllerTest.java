package com.example.transactionservice.controller;

import com.example.transactionservice.dto.TransferDTO;
import com.example.transactionservice.dto.TransferResponseDTO;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class TransactionControllerTest {

    private final TransactionService transactionService;
    private final WebTestClient webTestClient;

    private static final Transaction TRANSACTION = new Transaction(
            1L,
            "DEPOSITO",
            4L,
            1L,
            new BigDecimal("100000.00"),
            "Test deposit",
            LocalDateTime.of(2024, 5, 21, 10, 0)
    );

    private static final TransferDTO TRANSFER_DTO = new TransferDTO( 1L, 2L, new BigDecimal("500.00"));

    public TransactionControllerTest() {
        transactionService = Mockito.mock(TransactionService.class);
        webTestClient = WebTestClient.bindToController(new TransactionController(transactionService)).build();
    }

    @Test
    void getAll_ShouldReturnAllTransactions() {

        Mockito.when(transactionService.getAll())
                .thenReturn(Flux.just(TRANSACTION));

        webTestClient.get()
                .uri("/api/transfer")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Transaction.class)
                .hasSize(1)
                .value(transactions -> {
                    Assertions.assertNotNull(transactions);
                    Assertions.assertEquals("DEPOSITO", transactions.get(0).getType());
                    Assertions.assertEquals(new BigDecimal("100000.00"), transactions.get(0).getAmount());
                });

        Mockito.verify(transactionService).getAll();
    }

    @Test
    void create_ShouldSaveAndReturnTransaction() {

        Mockito.when(transactionService.create(Mockito.any(Transaction.class)))
                .thenReturn(Mono.just(TRANSACTION));

        webTestClient.post()
                .uri("/api/transfer/create")
                .bodyValue(TRANSACTION)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Transaction.class)
                .value(transaction -> {
                    Assertions.assertNotNull(transaction);
                    Assertions.assertEquals("DEPOSITO", transaction.getType());
                    Assertions.assertEquals(new BigDecimal("100000.00"), transaction.getAmount());
                });

        Mockito.verify(transactionService).create(Mockito.any(Transaction.class));
    }

    @Test
    void transfer_ShouldReturnTransferResponse() {

        Mockito.when(transactionService.makeTransaction(Mockito.any(TransferDTO.class)))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/transfer")
                .bodyValue(TRANSFER_DTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransferResponseDTO.class)
                .value(Assertions::assertNotNull);

        Mockito.verify(transactionService).makeTransaction(Mockito.any(TransferDTO.class));
    }
}
