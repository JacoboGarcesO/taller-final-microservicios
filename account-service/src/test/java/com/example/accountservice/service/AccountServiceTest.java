package com.example.accountservice.service;

import com.example.accountservice.dto.BankDTO;
import com.example.accountservice.grpc.TransactionConsumer;
import com.example.accountservice.grpc.Transaction;
import com.example.accountservice.model.Account;
import com.example.accountservice.repository.IAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

class AccountServiceTest {

    private final IAccountRepository accountRepository;
    private final GetBankClient getBankClient;
    private final TransactionConsumer transactionConsumer;
    private final AccountService accountService;

    private static final Account ACCOUNT = new Account(1L, "AHORROS", new BigDecimal("100000.00"), "active", 5L);
    private static final BankDTO BANK = new BankDTO(1L, "Bancolombia", "The best");

    public AccountServiceTest() {
        accountRepository = Mockito.mock(IAccountRepository.class);
        getBankClient = Mockito.mock(GetBankClient.class);
        transactionConsumer = Mockito.mock(TransactionConsumer.class);
        accountService = new AccountService(accountRepository, getBankClient, transactionConsumer);
    }

    @Test
    void getAll_ShouldReturnAllAccounts() {

        Mockito.when(accountRepository.findAll())
                .thenReturn(Flux.just(ACCOUNT));

        StepVerifier.create(accountService.getAll())
                .expectNext(ACCOUNT)
                .verifyComplete();

        Mockito.verify(accountRepository).findAll();

    }

    @Test
    void getById_ShouldReturnOneAccount() {

        Mockito.when(accountRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(ACCOUNT));

        StepVerifier.create(accountService.getById(1L))
                .expectNext(ACCOUNT)
                .verifyComplete();

        Mockito.verify(accountRepository).findById(Mockito.anyLong());

    }

    @Test
    void getById_ShouldReturnErrorWhenAccountNotFound() {

        Mockito.when(accountRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(accountService.getById(99L))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException && throwable.getMessage().equals("Account not found"))
                .verify();

        Mockito.verify(accountRepository).findById(Mockito.anyLong());
    }

    @Test
    void create_ShouldSaveAccountWhenBankExists() {

        Mockito.when(getBankClient.getBank(Mockito.anyLong()))
                .thenReturn(Mono.just(BANK));

        Mockito.when(accountRepository.save(Mockito.any(Account.class)))
                .thenReturn(Mono.just(ACCOUNT));

        StepVerifier.create(accountService.create(ACCOUNT))
                .expectNext(ACCOUNT)
                .verifyComplete();

        Mockito.verify(getBankClient).getBank(Mockito.anyLong());
        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
    }

    @Test
    void update_ShouldUpdateAccountWhenExists() {
        Mockito.when(accountRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(ACCOUNT));

        Mockito.when(getBankClient.getBank(Mockito.anyLong()))
                .thenReturn(Mono.just(BANK));

        Mockito.when(accountRepository.save(Mockito.any(Account.class)))
                .thenReturn(Mono.just(ACCOUNT));

        StepVerifier.create(accountService.update(ACCOUNT))
                .expectNext(ACCOUNT)
                .verifyComplete();

        Mockito.verify(accountRepository).findById(Mockito.anyLong());
        Mockito.verify(getBankClient).getBank(Mockito.anyLong());
        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
    }


    @Test
    void getMovements_ShouldReturnListOfTransactions() {

        List<Transaction> transactionList = List.of(
                Transaction.newBuilder()
                        .setId(1L)
                        .setType("DEPOSITO")
                        .setAccountNumber(1L)
                        .setAmount(50000.00)
                        .setDescription("Test deposit")
                        .setTimestamp("2024-05-21T10:00:00")
                        .build()
        );

        Mockito.when(transactionConsumer.getTransactions(Mockito.anyLong()))
                .thenReturn(transactionList);

        StepVerifier.create(accountService.getMovements(1L))
                .expectNextMatches(transactionDTO ->
                        transactionDTO.getId() == 1L &&
                        transactionDTO.getType().equals("DEPOSITO") &&
                        transactionDTO.getAccountNumber() == 1L &&
                        transactionDTO.getAmount().equals(BigDecimal.valueOf(50000.00)) &&
                        transactionDTO.getDescription().equals("Test deposit"))
                .verifyComplete();

        Mockito.verify(transactionConsumer).getTransactions(Mockito.anyLong());
    }

}