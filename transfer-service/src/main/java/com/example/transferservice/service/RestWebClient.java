package com.example.transferservice.service;

import com.example.transferservice.dto.AccountDTO;
import com.example.transferservice.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestWebClient {

  private final WebClient.Builder webClientBuilder;

  @Value("${account.service.url}")
  private String accountsServiceUrl;
  @Value("${transactions.service.url}")
  private String transactionsServiceUrl;

  public Mono<AccountDTO> getAccount(Long accountNumber) {
    return webClientBuilder
      .build()
      .get()
      .uri(accountsServiceUrl + "/api/accounts/" + accountNumber)
      .retrieve()
      .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Account does not exist")))
      .bodyToMono(AccountDTO.class);
  }

  public Mono<AccountDTO> updateAccount(AccountDTO account) {
    return webClientBuilder
            .build()
            .put()
            .uri(accountsServiceUrl + "/api/accounts")
            .bodyValue(account)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Account does not exist")))
            .bodyToMono(AccountDTO.class);
  }

  public Mono<Transaction> saveTransaction(Transaction transaction) {
    return webClientBuilder
            .build()
            .post()
            .uri(transactionsServiceUrl + "/api/transfer/create")
            .bodyValue(transaction)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Unsaved transaction")))
            .bodyToMono(Transaction.class);
  }
}
