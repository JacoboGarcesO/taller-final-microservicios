package com.example.accountservice.service;

import com.example.accountservice.dto.GetBankDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GetBankClient {
    private final WebClient.Builder webClientBuilder;
    private final String banksServiceUrl;

    public GetBankClient(WebClient.Builder webClientBuilder, @Value("${bank.service.url}") String banksServiceUrl){
        this.webClientBuilder = webClientBuilder;
        this.banksServiceUrl = banksServiceUrl;
    }

    public Mono<GetBankDTO> getBank(Long bankId) {
        return webClientBuilder
                .build()
                .get()
                .uri(banksServiceUrl + "/api/banks/" + bankId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.error(new RuntimeException("Bank not found")))
                .bodyToMono(GetBankDTO.class);
    }
}
