package com.example.bank.repository;

import com.example.bank.model.Bank;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BankRepository extends ReactiveCrudRepository<Bank, String> {
    Mono<Bank> findByName(String name);
}