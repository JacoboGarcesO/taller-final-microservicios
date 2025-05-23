package com.example.bankservice.service;

import com.example.bankservice.model.Bank;
import com.example.bankservice.repository.IBankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BankService {
    private final IBankRepository bankRepository;

    public Flux<Bank> getAll() {
        return bankRepository.findAll();
    }

    public Mono<Bank> getById(Long bankId) {
        return bankRepository.findById(bankId)
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not found")));
    }

    public Mono<Bank> create(Bank bank) {
        bank.setBankId(null);
        return bankRepository.save(bank);
    }

    public Mono<Bank> update(Bank bank) {
        return getById(bank.getBankId())
                .flatMap(existingBank -> {
                    existingBank.setName(bank.getName());
                    existingBank.setDescription(bank.getDescription());
                    return bankRepository.save(existingBank);
                });
    }

}
