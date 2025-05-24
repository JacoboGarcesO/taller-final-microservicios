package com.example.bankservice.service;

import com.example.bankservice.model.Bank;
import com.example.bankservice.repository.IBankRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankService {
    private final IBankRepository bankRepository;

    public BankService(IBankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Flux<Bank> getAll() {
        return bankRepository.findAll();
    }

    public Mono<Bank> getById(Long bankId) {
        return bankRepository
                .findById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found")));
    }

    public Mono<Bank> createBank(String nameBank) {
        return bankRepository.save(new Bank(null, nameBank));
    }

    public Mono<Bank> updateBank(Long bankId, Bank changeBank) {
        return bankRepository.findById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found")))
                .flatMap(existing -> {
                    existing.setName(changeBank.getName());
                    existing.setAddress(changeBank.getAddress());
                    return bankRepository.save(existing);
                })
                .doOnError(e -> System.err.println("❌ ERROR al actualizar banco: " + e.getMessage()));
    }
}
