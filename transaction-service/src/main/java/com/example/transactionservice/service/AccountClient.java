package com.example.transactionservice.service;

import com.example.transactionservice.dto.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountClient {

  private final WebClient.Builder webClientBuilder;

  @Value("${account.service.url}")
  private String accountsServiceUrl;

  public Mono<AccountDTO> getAccount(Long accountId) {
    return webClientBuilder
      .build()
      .get()
      .uri(accountsServiceUrl + "/api/accounts/" + accountId)
      .retrieve()
      .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Account does not exist 1")))
      .bodyToMono(AccountDTO.class);
  }

  public Mono<AccountDTO> updateAccount(AccountDTO account) {
    return webClientBuilder
            .build()
            .put()
            .uri(accountsServiceUrl + "/api/accounts")
            .bodyValue(account)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Account does not exist 2")))
            .bodyToMono(AccountDTO.class);
  }
}
