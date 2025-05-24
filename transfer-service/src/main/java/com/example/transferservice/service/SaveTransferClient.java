package com.example.transferservice.service;

import com.example.transferservice.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class SaveTransferClient {
    private final WebClient.Builder webClientBuilder;
    private final String transactionsServiceUrl;

    public SaveTransferClient(WebClient.Builder webClientBuilder, @Value("${transaction.service.url}") String transactionsServiceUrl){
        this.webClientBuilder = webClientBuilder;
        this.transactionsServiceUrl = transactionsServiceUrl;
    }

    public Mono<TransactionDTO> getTransaction(Long accountId) {
        return webClientBuilder
                .build()
                .get()
                .uri(transactionsServiceUrl + "/api/transactions/" + accountId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.error(new RuntimeException("Bank not found")))
                .bodyToMono(TransactionDTO.class);
    }

    public Mono<Void> saveTransfer(Long accountId, BigDecimal amount) {
        return webClientBuilder.build()
                .put()
                .uri(transactionsServiceUrl + "/api/transactions/{id}/credit?amount={amt}", accountId, amount)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
