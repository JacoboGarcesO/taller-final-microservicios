package com.example.accountservice.service;

import com.example.accountservice.dto.BankDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetBankClient {

  private final WebClient.Builder webClientBuilder;

  @Value("${bank.service.url}")
  private String productsServiceUrl;

  public Mono<BankDTO> getBank(Long bankId) {
    return webClientBuilder
      .build()
      .get()
      .uri(productsServiceUrl + "/api/banks/" + bankId)
      .retrieve()
      .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Bank does not exist")))
      .bodyToMono(BankDTO.class);
  }
}
