package com.example.bankservice.service;

import com.example.bankservice.model.Bank;
import com.example.bankservice.repository.IBankRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BankServiceTest {

    private final IBankRepository bankRepository;
    private final BankService bankService;

    private static final Bank BANK = new Bank(1L, "Bancolombia", "The best");

    public BankServiceTest() {
        bankRepository = Mockito.mock(IBankRepository.class);
        bankService = new BankService(bankRepository);
    }

    @Test
    void getAll_ShouldReturnAllBanks() {



        Mockito.when(bankRepository.findAll())
                .thenReturn(Flux.just(BANK));

        StepVerifier.create(bankService.getAll())
                .expectNext(BANK)
                .verifyComplete();

        Mockito.verify(bankRepository).findAll();

    }

    @Test
    void getById_ShouldReturnOneBank() {

        Mockito.when(bankRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(BANK));

        StepVerifier.create(bankService.getById(1L))
                .expectNext(BANK)
                .verifyComplete();

        Mockito.verify(bankRepository).findById(Mockito.anyLong());

    }

    @Test
    void getById_ShouldReturnErrorWhenBankNotFound() {

        Mockito.when(bankRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(bankService.getById(99L))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException && throwable.getMessage().equals("Bank not found"))
                .verify();

        Mockito.verify(bankRepository).findById(Mockito.anyLong());
    }


    @Test
    void create_ShouldReturnCreateBank() {

        Mockito.when(bankRepository.save(Mockito.any(Bank.class)))
                .thenReturn(Mono.just(BANK));

        StepVerifier.create(bankService.create(BANK))
                .expectNext(BANK)
                .verifyComplete();

        Mockito.verify(bankRepository).save(Mockito.any(Bank.class));

    }

    @Test
    void update_ShouldReturnUpdateBank() {

        Mockito.when(bankRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(BANK));

        Mockito.when(bankRepository.save(Mockito.any(Bank.class)))
                .thenReturn(Mono.just(BANK));

        StepVerifier.create(bankService.update(BANK))
                .expectNext(BANK)
                .verifyComplete();

        Mockito.verify(bankRepository).findById(Mockito.anyLong());
        Mockito.verify(bankRepository).save(Mockito.any(Bank.class));

    }
}