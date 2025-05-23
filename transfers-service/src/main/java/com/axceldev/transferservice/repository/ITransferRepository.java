package com.axceldev.transferservice.repository;

import com.axceldev.transferservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransferRepository extends ReactiveCrudRepository<Transaction, Long> {
}
