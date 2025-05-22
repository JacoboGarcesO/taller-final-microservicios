package com.axceldev.accountservice.repository;

import com.axceldev.accountservice.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends ReactiveCrudRepository<Account, Long> {
}
