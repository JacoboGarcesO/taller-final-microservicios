package com.axceldev.transactionservice.repository;

import com.axceldev.transactionservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ITransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
    Flux<Transaction> findByAccountNumber(String accountNumber);
}
