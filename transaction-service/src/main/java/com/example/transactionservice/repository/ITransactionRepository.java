package com.example.transactionservice.repository;

import com.example.transactionservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ITransactionRepository extends ReactiveCrudRepository<Transaction, Long>{
    Mono<Transaction> findById(Long transactionId);
    Flux<Transaction> findByAccountId(Long accountId);
}