package com.example.transactionservice.service;

import com.example.transactionservice.dto.GetAccountDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class GetAccountClient {
    private final WebClient.Builder webClientBuilder;
    private final String accountsServiceUrl;

    public GetAccountClient(WebClient.Builder webClientBuilder, @Value("${account.service.url}") String accountsServiceUrl){
        this.webClientBuilder = webClientBuilder;
        this.accountsServiceUrl = accountsServiceUrl;
    }

    public Mono<GetAccountDTO> getAccount(Long accountId) {
        return webClientBuilder
                .build()
                .get()
                .uri(accountsServiceUrl + "/api/accounts/" + accountId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.error(new RuntimeException("Bank not found")))
                .bodyToMono(GetAccountDTO.class);
    }

    public Mono<Void> debit(Long accountId, BigDecimal amount) {
        return webClientBuilder.build()
                .put()
                .uri(accountsServiceUrl + "/api/accounts/{id}/debit?amount={amt}", accountId, amount)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> credit(Long accountId, BigDecimal amount) {
        return webClientBuilder.build()
                .put()
                .uri(accountsServiceUrl + "/api/accounts/{id}/credit?amount={amt}", accountId, amount)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
