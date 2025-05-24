package com.example.accountservice.service;

import com.example.accountservice.dto.GetBankDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.repository.IAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService {
    private final IAccountRepository accountRepository;
    private final GetBankClient getBankClient;

    public AccountService(
            IAccountRepository accountRepository,
            GetBankClient getBankClient
    ) {
        this.accountRepository = accountRepository;
        this.getBankClient = getBankClient;
    }

    public Flux<Account> getAll() {
        return accountRepository.findAll();
    }

    public Mono<Account> getById(Long accountId) {
        return accountRepository
                .findById(accountId)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")));
    }

    public Mono<Account> createAccount(Account newAccount) {
        return getBankClient.getBank(newAccount.getBank_id())
                .switchIfEmpty(Mono.error(new RuntimeException("Bank not exist")))
                .flatMap(bank -> {
                    return accountRepository.save(newAccount);
                });
    }

    public Mono<Account> updateAccount(Long accountId, Account changeAccount) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")))
                .flatMap(existing -> {
                    existing.setType(changeAccount.getType());
                    existing.setNumber(changeAccount.getNumber());
                    return accountRepository.save(existing);
                })
                .doOnError(e -> System.err.println("❌ ERROR al actualizar cuenta: " + e.getMessage()));
    }
}
