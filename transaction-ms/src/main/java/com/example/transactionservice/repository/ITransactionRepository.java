package com.example.transactionservice.repository;

import com.example.transactionservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ITransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
    Flux<Transaction> findByAccountNumber(Long accountId);
}
