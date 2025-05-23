package com.axceldev.transactionservice.service;

import com.axceldev.transactionservice.dto.BankCheckResponse;
import com.axceldev.transactionservice.dto.CreateTransactionRequest;
import com.axceldev.transactionservice.dto.TransactionMessageDto;
import com.axceldev.transactionservice.dto.UpdateBalanceRequest;
import com.axceldev.transactionservice.model.Transaction;
import com.axceldev.transactionservice.model.TransactionType;
import com.axceldev.transactionservice.repository.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final ITransactionRepository transactionRepository;
    private final WebClient webClient;
    private final RabbitTemplate rabbitTemplate;
    @Value("${app.transaction.exchange}")
    private String transactionExchange;
    @Value("${app.transaction.routing-key}")
    private String transactionRoutingKey;

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
        LocalDateTime now = LocalDateTime.now();
        Transaction withdrawal = buildTransactionWithdrawal(request, now);
        Transaction deposit = buildTransactionDeposit(request, now);

        return transactionRepository.saveAll(List.of(withdrawal, deposit))
                .collectList()
                .flatMap(transactions -> updateBalance(transactions)
                        .thenReturn(transactions));
    }

    private Transaction buildTransactionWithdrawal(CreateTransactionRequest request, LocalDateTime currentDateTime) {
        return Transaction.builder()
                .accountNumber(request.sourceAccountNumber())
                .amount(request.amount())
                .currency(request.currency())
                .transactionType(TransactionType.WITHDRAWAL)
                .createdAt(currentDateTime)
                .build();
    }

    private Transaction buildTransactionDeposit(CreateTransactionRequest request,  LocalDateTime currentDateTime) {
        return Transaction.builder()
                .accountNumber(request.destinationAccountNumber())
                .amount(request.amount())
                .currency(request.currency())
                .transactionType(TransactionType.DEPOSIT)
                .createdAt(currentDateTime)
                .build();
    }

    private Mono<Boolean> updateBalance(List<Transaction> transactions) {
        return Flux.fromIterable(transactions)
                .delayElements(Duration.ofMillis(500))
                .flatMap(transaction -> {
                    UpdateBalanceRequest request = new UpdateBalanceRequest(
                            transaction.getAccountNumber(),
                            transaction.getAmount(),
                            transaction.getTransactionType()
                    );
                    return webClient.post()
                            .uri("/api/accounts/update-balance")
                            .bodyValue(request)
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .onErrorReturn(false);
                })
                .all(Boolean::booleanValue);
    }

    public Mono<Boolean> areAccountsFromSameBank(String sourceAccount, String destinationAccount) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/accounts/batch")
                        .queryParam("sourceAccountNumber", sourceAccount)
                        .queryParam("destinationAccountNumber", destinationAccount)
                        .build()
                )
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
                    LocalDateTime now = LocalDateTime.now();
                    Transaction withdrawal = buildTransactionWithdrawal(request, now);
                    Transaction deposit = buildTransactionDeposit(request, now);
                    return transactionRepository.save(withdrawal)
                            .flatMap(transaction -> updateBalance(List.of(transaction)))
                            .doOnSuccess(tx -> sendDepositToQueue(request))
                            .then(Mono.just(List.of(withdrawal, deposit)));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Fondos insuficientes")));
    }

    private void sendDepositToQueue(CreateTransactionRequest request) {
        logger.info(String.format("Sending deposit to queue: %s", request.toString()));
        rabbitTemplate.convertAndSend(transactionExchange, transactionRoutingKey, buildTransactionMessage(request));
    }

    private TransactionMessageDto buildTransactionMessage(CreateTransactionRequest request) {
        return TransactionMessageDto.builder()
                .accountNumber(request.destinationAccountNumber())
                .transactionType(TransactionType.DEPOSIT)
                .amount(request.amount())
                .currency(request.currency())
                .build();
    }

    private Transaction buildTransaction(CreateTransactionRequest request) {
        LocalDateTime now = LocalDateTime.now();
        return Transaction.builder()
                .accountNumber(request.sourceAccountNumber())
                .amount(request.amount())
                .currency(request.currency())
                .createdAt(now)
                .transactionType(TransactionType.WITHDRAWAL)
                .build();
    }
}
