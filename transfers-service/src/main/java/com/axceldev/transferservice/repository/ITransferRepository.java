package com.axceldev.transferservice.repository;

import com.axceldev.transferservice.model.Transfer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransferRepository extends ReactiveCrudRepository<Transfer, Long> {
}
