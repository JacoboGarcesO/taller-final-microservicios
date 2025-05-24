package com.example.cuentasservice.service;

import com.example.cuentasservice.dto.BancoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GetBanco {

  private final WebClient.Builder webClientBuilder;

  @Value("${banco.service.url}")
  private String productsServiceUrl;

  public GetBanco(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  public Mono<BancoDTO> getBanco(Long bancoId) {
    return webClientBuilder
            .build()
            .get()
            .uri(productsServiceUrl + "/api/bancos/" + bancoId)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                    Mono.error(new RuntimeException("Banco no encontrado")))
            .bodyToMono(BancoDTO.class)
            .switchIfEmpty(Mono.error(new RuntimeException("Banco no encontrado")));
  }
}
