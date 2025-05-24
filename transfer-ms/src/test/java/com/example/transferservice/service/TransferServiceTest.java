package com.example.transferservice.service;


import com.example.transferservice.dto.AccountDTO;
import com.example.transferservice.model.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

class TransferServiceTest {

    private final RestWebClient restWebClient;
    private final TransferService transferService;

    private static final Transaction TRANSACTION = new Transaction(
            null,
            "DEPOSITO",
            2L,
            1L,
            new BigDecimal("100000.00"),
            "Transferencia interbancaria",
            null
    );

    private static final AccountDTO ACCOUNT = new AccountDTO(
            2L,
            "AHORROS",
            new BigDecimal("50000.00"),
            "active",
            99L
    );

    public TransferServiceTest() {
        restWebClient = Mockito.mock(RestWebClient.class);
        transferService = new TransferService(restWebClient);
    }

    @Test
    void saveTransfer_ShouldUpdateAccountAndSaveTransaction() {

        AccountDTO updatedAccount = new AccountDTO(
                ACCOUNT.getAccountNumber(),
                ACCOUNT.getAccountType(),
                ACCOUNT.getBalance().add(TRANSACTION.getAmount()),
                ACCOUNT.getStatus(),
                ACCOUNT.getBankId()
        );

        Mockito.when(restWebClient.getAccount(Mockito.anyLong()))
                .thenReturn(Mono.just(ACCOUNT));
        Mockito.when(restWebClient.updateAccount(Mockito.any(AccountDTO.class)))
                .thenReturn(Mono.just(updatedAccount));
        Mockito.when(restWebClient.saveTransaction(Mockito.any(Transaction.class)))
                .thenReturn(Mono.just(TRANSACTION));

        StepVerifier.create(transferService.saveTransfer(TRANSACTION))
                .verifyComplete();

        Mockito.verify(restWebClient).getAccount(Mockito.anyLong());
        Mockito.verify(restWebClient).updateAccount(Mockito.any(AccountDTO.class));
        Mockito.verify(restWebClient).saveTransaction(Mockito.any(Transaction.class));
    }

    @Test
    void saveTransfer_ShouldFailIfAccountNotFound() {

        Mockito.when(restWebClient.getAccount(Mockito.anyLong()))
                .thenReturn(Mono.error(new RuntimeException("Account not found")));

        StepVerifier.create(transferService.saveTransfer(TRANSACTION))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException && throwable.getMessage().equals("Account not found"))
                .verify();

        Mockito.verify(restWebClient).getAccount(Mockito.anyLong());
        Mockito.verify(restWebClient, Mockito.never()).updateAccount(Mockito.any());
        Mockito.verify(restWebClient, Mockito.never()).saveTransaction(Mockito.any());
    }
}
