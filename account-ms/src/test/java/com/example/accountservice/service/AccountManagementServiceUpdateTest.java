package com.example.accountservice.service;

import com.example.accountservice.dto.BankDTO;
import com.example.accountservice.consumer.TransactionConsumer;
import com.example.accountservice.model.Account;
import com.example.accountservice.repository.IAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

class AccountManagementServiceUpdateTest {

    private IAccountRepository accountRepository;
    private GetBankClient getBankClient;
    private TransactionConsumer transactionConsumer;
    private AccountManagementService accountManagementService;

    private static final Account TEST_ACCOUNT = new Account(
            1L, "AHORROS", new BigDecimal("100000.00"), "active", 5L
    );

    private static final BankDTO TEST_BANK = new BankDTO(
            5L, "Banco de Prueba", "Banco de integración"
    );

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(IAccountRepository.class);
        getBankClient = Mockito.mock(GetBankClient.class);
        transactionConsumer = Mockito.mock(TransactionConsumer.class);

        accountManagementService = new AccountManagementService(accountRepository, getBankClient, transactionConsumer);
    }

    @Test
    void shouldUpdateAccountSuccessfullyWhenExists() {
        // Mock para que findById no retorne null
        Mockito.when(accountRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(TEST_ACCOUNT));

        // Mock para banco válido
        Mockito.when(getBankClient.getBank(Mockito.anyLong()))
                .thenReturn(Mono.just(TEST_BANK));

        // Mock para guardar la cuenta actualizada
        Mockito.when(accountRepository.save(Mockito.any(Account.class)))
                .thenReturn(Mono.just(TEST_ACCOUNT));

        // Ejecutar el flujo y verificar
        StepVerifier.create(accountManagementService.update(TEST_ACCOUNT))
                .expectNextMatches(account ->
                        account.getAccountNumber() == 1L &&
                                account.getAccountType().equals("AHORROS") &&
                                account.getBalance().compareTo(new BigDecimal("100000.00")) == 0)
                .verifyComplete();

        Mockito.verify(accountRepository).findById(Mockito.anyLong());
        Mockito.verify(getBankClient).getBank(Mockito.anyLong());
        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
    }
}
