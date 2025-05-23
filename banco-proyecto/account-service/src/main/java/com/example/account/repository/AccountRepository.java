package com.example.account.repository;

import com.example.account.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Flux<Account> findByBankCode(String bankCode);
}