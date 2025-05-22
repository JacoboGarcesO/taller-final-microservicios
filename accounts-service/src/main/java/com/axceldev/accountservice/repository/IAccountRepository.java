package com.axceldev.accountservice.repository;

import com.axceldev.accountservice.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends ReactiveCrudRepository<Account, Long> {
    Flux<Account> findByAccountNumberIn(List<String> accountNumber);

    Mono<Account> findByAccountNumber(String accountNumber);
}
