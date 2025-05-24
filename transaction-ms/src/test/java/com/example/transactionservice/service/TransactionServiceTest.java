package com.example.transactionservice.service;

import com.example.transactionservice.dto.AccountDTO;
import com.example.transactionservice.dto.TransferDTO;
import com.example.transactionservice.event.TransferEventPublisher;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.ITransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class TransactionServiceTest {

    private final ITransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final TransferEventPublisher transferEventPublisher;
    private final TransactionService transactionService;

    private static final Transaction TRANSACTION = new Transaction(
            1L,
            "DEPOSITO",
            4L,
            1L,
            new BigDecimal("100000.00"),
            "Test deposit",
            LocalDateTime.of(2024, 5, 21, 10, 0)
    );

    public TransactionServiceTest() {
        transactionRepository = Mockito.mock(ITransactionRepository.class);
        accountClient = Mockito.mock(AccountClient.class);
        transferEventPublisher = Mockito.mock(TransferEventPublisher.class);
        transactionService = new TransactionService(transactionRepository, accountClient, transferEventPublisher);
    }

    @Test
    void getAll_ShouldReturnAllTransactions() {

        Mockito.when(transactionRepository.findAll())
                .thenReturn(Flux.just(TRANSACTION));

        StepVerifier.create(transactionService.getAll())
                .expectNext(TRANSACTION)
                .verifyComplete();

        Mockito.verify(transactionRepository).findAll();

    }

    @Test
    void create_ShouldSaveTransaction() {

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
                .thenReturn(Mono.just(TRANSACTION));

        StepVerifier.create(transactionService.create(TRANSACTION))
                .expectNext(TRANSACTION)
                .verifyComplete();

        Mockito.verify(transactionRepository).save(Mockito.any(Transaction.class));
    }

    @Test
    void makeTransaction_ShouldTransferBetweenSameBankAccounts() {
        AccountDTO source = new AccountDTO(1L, "AHORROS", new BigDecimal("200000.00"), "active", 10L);
        AccountDTO destination = new AccountDTO(2L, "AHORROS", new BigDecimal("50000.00"), "active", 10L);
        TransferDTO transfer = new TransferDTO(source.getAccountNumber(), destination.getAccountNumber(), new BigDecimal("100000.00"));

        Mockito.when(accountClient.getAccount(source.getAccountNumber()))
                .thenReturn(Mono.just(source));
        Mockito.when(accountClient.getAccount(destination.getAccountNumber()))
                .thenReturn(Mono.just(destination));
        Mockito.when(accountClient.updateAccount(Mockito.any(AccountDTO.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
                .thenReturn(Mono.just(new Transaction()));

        StepVerifier.create(transactionService.makeTransaction(transfer))
                .verifyComplete();

        Mockito.verify(accountClient).getAccount(source.getAccountNumber());
        Mockito.verify(accountClient).getAccount(destination.getAccountNumber());
        Mockito.verify(accountClient, Mockito.times(2)).updateAccount(Mockito.any(AccountDTO.class));
        Mockito.verify(transactionRepository, Mockito.times(2)).save(Mockito.any(Transaction.class));
    }

    @Test
    void makeTransaction_ShouldTransferBetweenDifferentBanks() {
        AccountDTO source = new AccountDTO(1L, "AHORROS", new BigDecimal("200000.00"), "active", 10L);
        AccountDTO destination = new AccountDTO(2L, "AHORROS", new BigDecimal("50000.00"), "active", 11L);
        TransferDTO transfer = new TransferDTO(source.getAccountNumber(), destination.getAccountNumber(), new BigDecimal("100000.00"));

        Mockito.when(accountClient.getAccount(source.getAccountNumber()))
                .thenReturn(Mono.just(source));
        Mockito.when(accountClient.getAccount(destination.getAccountNumber()))
                .thenReturn(Mono.just(destination));
        Mockito.when(accountClient.updateAccount(Mockito.any(AccountDTO.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
                .thenReturn(Mono.just(new Transaction()));

        StepVerifier.create(transactionService.makeTransaction(transfer))
                .verifyComplete();

        Mockito.verify(accountClient).getAccount(source.getAccountNumber());
        Mockito.verify(accountClient).getAccount(destination.getAccountNumber());
        Mockito.verify(accountClient).updateAccount(source);
        Mockito.verify(transactionRepository).save(Mockito.argThat(t -> t.getType().equals("RETIRO")));
        Mockito.verify(transferEventPublisher).publishPurchaseMadeEvent(Mockito.any(Transaction.class));
    }


    @Test
    void makeTransaction_ShouldReturnErrorWhenInsufficientFunds() {
        AccountDTO source = new AccountDTO(1L, "AHORROS", new BigDecimal("200000.00"), "active", 10L);
        AccountDTO destination = new AccountDTO(2L, "AHORROS", new BigDecimal("50000.00"), "active", 10L);
        TransferDTO transfer = new TransferDTO(source.getAccountNumber(), destination.getAccountNumber(), new BigDecimal("300000.00"));

        Mockito.when(accountClient.getAccount(source.getAccountNumber()))
                .thenReturn(Mono.just(source));
        Mockito.when(accountClient.getAccount(destination.getAccountNumber()))
                .thenReturn(Mono.just(destination));

        StepVerifier.create(transactionService.makeTransaction(transfer))
                .expectErrorMatches(error -> error instanceof RuntimeException && error.getMessage().equals("Insufficient funds"))
                .verify();

        Mockito.verify(accountClient).getAccount(source.getAccountNumber());
        Mockito.verify(accountClient).getAccount(destination.getAccountNumber());
        Mockito.verify(transactionRepository, Mockito.never()).save(Mockito.any(Transaction.class));
        Mockito.verify(transferEventPublisher, Mockito.never()).publishPurchaseMadeEvent(Mockito.any());
    }
}