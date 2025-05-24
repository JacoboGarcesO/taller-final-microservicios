package com.example.transactionservice.service;

import com.example.transactionservice.dto.GetAccountDTO;
import com.example.transactionservice.event.TransferEventPublisher;
import com.example.transactionservice.model.Transaction;

import com.example.transactionservice.model.TransferRequest;
import com.example.transactionservice.repository.ITransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class TransactionService {
    private final ITransactionRepository transactionRepository;
    private final TransferEventPublisher transferEventPublisher;
    private final GetAccountClient getAccountClient;

    public TransactionService(
            ITransactionRepository transactionRepository,
            TransferEventPublisher transferEventPublisher,
            GetAccountClient getAccountClient) {
        this.transactionRepository = transactionRepository;
        this.transferEventPublisher = transferEventPublisher;
        this.getAccountClient = getAccountClient;
    }

    public Mono<Void> processTransfer(TransferRequest request) {
        boolean mismaEntidad = request.getSourceBankId().equals(request.getDestinationBankId());

        return validarFondos(request.getSourceAccountId(), request.getAmount())
                .then(registrarRetiro(request.getSourceAccountId(), request.getAmount()))
                .then(
                        mismaEntidad
                                ? registrarDeposito(request.getDestinationAccountId(), request.getAmount())
                                : transferEventPublisher.sendTransferToQueue(request)
                );
    }


    public Mono<Void> registrarRetiro(Long accountId, BigDecimal amount) {
        return getAccountClient.debit(accountId, amount)
                .then(transactionRepository.save(
                        new Transaction(
                                null,
                                accountId,
                                "retiro",
                                amount.negate(),
                                LocalDateTime.now())).then())
                .doOnSuccess(v -> System.out.println("✅ Retiro completado"))
                .doOnError(e -> System.err.println("❌ Error al hacer retiro: " + e.getMessage()));
    }

    public Mono<Void> registrarDeposito(Long accountId, BigDecimal amount) {
        return getAccountClient.credit(accountId, amount)
                .then(transactionRepository.save(
                        new Transaction(
                                null,
                                accountId,
                                "deposito",
                                amount.negate(),
                                LocalDateTime.now())).then())
                .doOnSuccess(v -> System.out.println("✅ Depósito completado"))
                .doOnError(e -> System.err.println("❌ Error al hacer depósito: " + e.getMessage()));
    }

    private Mono<Boolean> validarFondos(Long accountId, BigDecimal amt) {
        System.out.println("Validando...");
        return getAccountClient.getAccount(accountId)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")))
                .flatMap(acct -> acct.getBalance().compareTo(amt) >= 0 ? Mono.just(true)
                        : Mono.error(new RuntimeException("Fondos insuficientes")));
    }
}
