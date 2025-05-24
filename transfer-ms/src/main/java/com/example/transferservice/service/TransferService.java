package com.example.transferservice.service;

import com.example.transferservice.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final RestWebClient restWebClient;

    public Mono<Void> saveTransfer(Transaction deposit) {
        return restWebClient.getAccount(deposit.getAccountNumber())
                .flatMap(destination -> {
                    destination.setBalance(destination.getBalance().add(deposit.getAmount()));
                    return Mono.when(
                            restWebClient.updateAccount(destination),
                            restWebClient.saveTransaction(deposit)
                    ).then();
                });
    }

}
