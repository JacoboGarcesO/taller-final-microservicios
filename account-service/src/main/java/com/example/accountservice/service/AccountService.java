package com.example.accountservice.service;

import com.example.accountservice.dto.TransactionDTO;
import com.example.accountservice.grpc.TransactionConsumer;
import com.example.accountservice.model.Account;
import com.example.accountservice.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final IAccountRepository accountRepository;
    private final GetBankClient getBankClient;
    private final TransactionConsumer transactionConsumer;

    public Flux<Account> getAll() {
        return accountRepository.findAll();
    }

    public Mono<Account> getById(Long accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")));
    }

    public Mono<Account> create(Account account) {
        account.setAccountNumber(null);
        return getBankClient.getBank(account.getBankId())
                .flatMap(bank -> accountRepository.save(account));
    }

    public Mono<Account> update(Account account) {
        return getById(account.getAccountNumber())
                .flatMap(existingAccount ->
                        getBankClient.getBank(account.getBankId())
                                .flatMap(bank -> {
                                    existingAccount.setAccountNumber(account.getAccountNumber());
                                    existingAccount.setAccountType(account.getAccountType());
                                    existingAccount.setBalance(account.getBalance());
                                    existingAccount.setStatus(account.getStatus());
                                    existingAccount.setBankId(account.getBankId());
                                    return accountRepository.save(existingAccount);
                                }));
    }

    public Flux<TransactionDTO> getMovements(Long accountNumber) {
        return Flux.fromIterable(transactionConsumer.getTransactions(accountNumber))
                .map(transaction -> new TransactionDTO(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAccountNumber(),
                        transaction.getBankId(),
                        BigDecimal.valueOf(transaction.getAmount()),
                        transaction.getDescription(),
                        LocalDateTime.parse(transaction.getTimestamp())
                ));
    }

}
