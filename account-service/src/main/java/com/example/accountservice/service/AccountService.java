package com.example.accountservice.service;

import com.example.accountservice.dto.TransactionDTO;
import com.example.accountservice.grpc.TransactionConsumer;
import com.example.accountservice.grpc.TransactionList;
import com.example.accountservice.grpc.TransactionRequest;
import com.example.accountservice.grpc.TransactionResponse;
import com.example.accountservice.model.Account;
import com.example.accountservice.repository.IAccountRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    private final IAccountRepository accountRepository;
    private final GetBankClient getBankClient;
    private final TransactionConsumer transactionConsumer;

    public AccountService(
            IAccountRepository accountRepository,
            GetBankClient getBankClient,
            TransactionConsumer transactionConsumer
    ) {
        this.accountRepository = accountRepository;
        this.getBankClient = getBankClient;
        this.transactionConsumer = transactionConsumer;
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
                    existing.setBalance(changeAccount.getBalance());
                    existing.setNumber(changeAccount.getNumber());
                    return accountRepository.save(existing);
                })
                .doOnError(e -> System.err.println("❌ ERROR al actualizar cuenta: " + e.getMessage()));
    }

    public Mono<Account> debit(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new IllegalArgumentException("El monto debe ser positivo"));
        }

        return accountRepository.findById(id)
                .flatMap(account -> {
                    if (account.getBalance().compareTo(amount) >= 0) {
                        account.setBalance(account.getBalance().subtract(amount));
                        return accountRepository.save(account);
                    } else {
                        return Mono.error(new RuntimeException("Fondos insuficientes"));
                    }
                });
    }

    public Mono<Account> credit(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new IllegalArgumentException("El monto debe ser positivo"));
        }

        return accountRepository.findById(id)
                .flatMap(account -> {
                    account.setBalance(account.getBalance().add(amount));
                    return accountRepository.save(account);
                });
    }

    public List<TransactionDTO> obtenerMovimientos(Long accountId) {
        TransactionList response = transactionConsumer.getTransaction(accountId);

        return response.getTransactionsList().stream()
                .map(tx -> new TransactionDTO(
                        tx.getId(),
                        tx.getAccountId(),
                        tx.getType(),
                        BigDecimal.valueOf(tx.getAmount()),
                        LocalDateTime.parse(tx.getDate())  // <-- Esto puede fallar si no es ISO 8601
                ))
                .toList();
    }


}
