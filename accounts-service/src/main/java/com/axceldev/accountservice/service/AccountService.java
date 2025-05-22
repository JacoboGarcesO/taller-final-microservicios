package com.axceldev.accountservice.service;

import com.axceldev.accountservice.dto.AccountNumberBankIdResponse;
import com.axceldev.accountservice.dto.CreateAccountRequest;
import com.axceldev.accountservice.dto.HasSufficientFundsRequest;
import com.axceldev.accountservice.model.Account;
import com.axceldev.accountservice.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final IAccountRepository accountRepository;
    private final WebClient webClient;


    public Mono<Account> createAccount(CreateAccountRequest request){
        return existsBank(request.bankId())
                .filter("true"::equals)
                .flatMap(response -> {
                    Account account = buildAccount(request);
                    return accountRepository.save(account);
                })
                .onErrorResume(throwable ->
                        Mono.error(new RuntimeException("Error creating account", throwable))
                ).switchIfEmpty(Mono.error(new RuntimeException("Bank not found")));
    }


    public Flux<AccountNumberBankIdResponse> findByAccountNumbers(List<String> accountNumbers) {
        return accountRepository.findByAccountNumberIn(accountNumbers)
                .map(account -> new AccountNumberBankIdResponse(
                        account.getAccountNumber(), account.getBankId())
                )
                .switchIfEmpty(Flux.error(new RuntimeException("No accounts found")))
                .onErrorResume(throwable ->
                        Flux.error(new RuntimeException("Error fetching accounts", throwable))
                );
    }

    public Mono<Boolean> hasSufficientFunds(HasSufficientFundsRequest request) {
        return getBalance(request.accountNumber())
                .map(balance -> balance >= request.amount())
                .onErrorResume(throwable ->
                        Mono.error(new RuntimeException("Error checking balance", throwable))
                );
    }

    public Mono<Double> getBalance(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(Account::getBalance)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")))
                .onErrorResume(throwable ->
                        Mono.error(new RuntimeException("Error fetching balance", throwable))
                );
    }

    private Mono<String> existsBank(Long bankId) {
        return webClient.get()
                .uri("/api/banks/{bankId}", bankId)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable ->
                        Mono.error(new RuntimeException("Error checking bank existence", throwable))
                );
    }

    private Account buildAccount(CreateAccountRequest request) {
        Double balanceInit = 0.0;
        return Account.builder()
                .accountNumber(request.accountNumber())
                .accountType(request.accountType())
                .currency(request.currency())
                .balance(balanceInit)
                .bankId(request.bankId())
                .build();
    }

}
