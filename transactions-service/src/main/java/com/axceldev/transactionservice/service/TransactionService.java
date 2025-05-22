package com.axceldev.transactionservice.service;

import com.axceldev.transactionservice.dto.BankCheckResponse;
import com.axceldev.transactionservice.dto.CreateTransactionRequest;
import com.axceldev.transactionservice.model.Transaction;
import com.axceldev.transactionservice.model.TransactionType;
import com.axceldev.transactionservice.repository.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final ITransactionRepository transactionRepository;
    private final WebClient webClient;
    private final RabbitTemplate rabbitTemplate;
    @Value("${app.transaction.queue}")
    private String transactionQueue;

    public Mono<List<Transaction>> createTransaction(CreateTransactionRequest request) {
        return areAccountsFromSameBank(request.sourceAccountNumber(), request.destinationAccountNumber())
                .filter(Boolean::booleanValue)
                .flatMap(response ->
                        hasSufficientFunds(request.sourceAccountNumber(), request.amount())
                                .filter(Boolean::booleanValue)
                                .flatMap(hasFunds -> saveWithdrawalAndDeposit(request))
                                .switchIfEmpty(Mono.error(new RuntimeException("Fondos insuficientes")))
                )
                .onErrorResume(throwable ->
                        Mono.error(new RuntimeException("Error creando la transacción", throwable))
                )
                .switchIfEmpty(processInterbankTransaction(request));
    }

    private Mono<Boolean> hasSufficientFunds(String accountNumber, Double amount) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/accounts/has-sufficient-funds")
                        .queryParam("accountNumber", accountNumber)
                        .queryParam("amount", amount)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(hasFunds -> hasFunds.equals("true"))
                .onErrorResume(throwable -> {
                    Mono.error(new RuntimeException("Error consultando fondos", throwable));
                    return Mono.just(false);
                });
    }

    private Mono<List<Transaction>> saveWithdrawalAndDeposit(CreateTransactionRequest request) {

        Transaction withdrawal = Transaction.builder()
                .accountNumber(request.sourceAccountNumber())
                .amount(-Math.abs(request.amount()))
                .currency(request.currency())
                .transactionType(TransactionType.WITHDRAWAL)
                .build();

        Transaction deposit = Transaction.builder()
                .accountNumber(request.destinationAccountNumber())
                .amount(Math.abs(request.amount()))
                .currency(request.currency())
                .transactionType(TransactionType.DEPOSIT)
                .build();

        return transactionRepository.saveAll(List.of(withdrawal, deposit)).collectList();
    }

    public Mono<Boolean> areAccountsFromSameBank(String sourceAccount, String destinationAccount) {
        return webClient.post()
                .uri("/api/accounts/batch")
                .bodyValue(List.of(sourceAccount, destinationAccount))
                .retrieve()
                .bodyToFlux(BankCheckResponse.class)
                .collectList()
                .map(responses -> {
                    if (responses.size() == 2) {
                        return Objects.equals(responses.get(0).bankId(), responses.get(1).bankId());
                    }
                    return false;
                });
    }

    private Mono<List<Transaction>> processInterbankTransaction(CreateTransactionRequest request) {
        return hasSufficientFunds(request.sourceAccountNumber(), request.amount())
                .filter(Boolean::booleanValue)
                .flatMap(hasFunds -> {
                    Transaction withdrawal = Transaction.builder()
                            .accountNumber(request.sourceAccountNumber())
                            .amount(request.amount())
                            .currency(request.currency())
                            .transactionType(TransactionType.WITHDRAWAL)
                            .build();

                    Transaction deposit = Transaction.builder()
                            .accountNumber(request.destinationAccountNumber())
                            .amount(request.amount())
                            .currency(request.currency())
                            .transactionType(TransactionType.DEPOSIT)
                            .build();

                    return transactionRepository.save(withdrawal)
                            .doOnSuccess(tx -> rabbitTemplate.convertAndSend(transactionQueue, deposit))
                            .then(Mono.just(List.of(withdrawal)));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Fondos insuficientes")));
    }
}
