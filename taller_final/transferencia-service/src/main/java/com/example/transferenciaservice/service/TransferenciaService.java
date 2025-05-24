package com.example.transferenciaservice.service;

import com.example.transferenciaservice.model.Transferencia;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransferenciaService {
    public TransferenciaService(RestWebClient restWebClient) {
        this.restWebClient = restWebClient;
    }

    private final RestWebClient restWebClient;

    public Mono<Void> saveTransferencia(Transferencia credito) {
        return restWebClient.getCuenta(credito.getCuenta().longValue())
                .flatMap(destination -> {
                    destination.setSaldo(destination.getSaldo().add(credito.getMonto()));
                    return Mono.when(
                            restWebClient.updateCuenta(destination),
                            restWebClient.saveTransferencia(credito)
                    ).then();
                });
    }

}
