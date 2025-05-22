package com.axceldev.transactionservice.repository;

import com.axceldev.transactionservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
}
