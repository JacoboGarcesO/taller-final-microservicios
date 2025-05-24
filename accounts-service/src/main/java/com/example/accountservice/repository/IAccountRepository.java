package com.example.accountservice.repository;

import com.example.accountservice.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends ReactiveCrudRepository<Account, Long> {
}
