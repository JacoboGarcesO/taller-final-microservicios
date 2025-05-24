package com.example.transaccionservice.service;

import com.example.transaccionservice.dto.CuentaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class CuentaService {
    private final WebClient.Builder webClientBuilder;

    @Value("${cuenta.service.url}")
    private String cuentaServiceUrl;

    public CuentaService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<CuentaDTO> getCuenta(Long cuenta) {
        return webClientBuilder
                .build()
                .get()
                .uri(cuentaServiceUrl + "/api/cuentas/" + cuenta)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .defaultIfEmpty("Cuenta" + cuenta + " no existe!!")
                                .flatMap(errorBody ->
                                        Mono.error(new RuntimeException("Error al obtener cuenta " + cuenta + ": " + errorBody))
                                )
                )
                .bodyToMono(CuentaDTO.class);
    }

    public Mono<CuentaDTO> updateCuenta(CuentaDTO cuenta) {
        if (cuenta.getSaldoSobregiro() == null) {
            cuenta.setSaldoSobregiro(BigDecimal.ZERO);
        }
        System.out.println("DTO enviado al actualizar cuenta: " + cuenta);
        return webClientBuilder
                .build()
                .put()
                .uri(cuentaServiceUrl + "/api/cuentas")
                .bodyValue(cuenta)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.err.println("Error del servicio cuentas: " + errorBody);
                                    return Mono.error(new RuntimeException("Error actualizando cuenta: " + errorBody));
                                })
                )
                .bodyToMono(CuentaDTO.class);
    }
}
