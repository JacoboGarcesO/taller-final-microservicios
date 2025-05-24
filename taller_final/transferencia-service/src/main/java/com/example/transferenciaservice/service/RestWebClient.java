package com.example.transferenciaservice.service;

import com.example.transferenciaservice.dto.CuentaDTO;
import com.example.transferenciaservice.model.Transferencia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestWebClient {
    public RestWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    private final WebClient.Builder webClientBuilder;

    @Value("${cuenta.service.url}")
    private String cuentaServiceUrl;
    @Value("${transacciones.service.url}")
    private String transaccionesServiceUrl;

    public Mono<CuentaDTO> getCuenta(Long cuentaNumber) {
        String url = cuentaServiceUrl + "/api/cuentas/" + cuentaNumber;
        System.out.println("Consultando cuenta en: " + url);

        return webClientBuilder
                .build()
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .defaultIfEmpty("Cuenta no encontrada")
                                .flatMap(errorBody -> {
                                    System.err.println("Error al obtener cuenta " + cuentaNumber + ": " + errorBody);
                                    return Mono.error(new RuntimeException("Cuenta " + cuentaNumber + " no existe: " + errorBody));
                                })
                )
                .bodyToMono(CuentaDTO.class)
                .doOnError(e -> System.err.println("Error fatal en getCuenta(" + cuentaNumber + "): " + e.getMessage()));
    }

    public Mono<CuentaDTO> updateCuenta(CuentaDTO cuenta) {
        return webClientBuilder
                .build()
                .put()
                .uri(cuentaServiceUrl + "/api/cuentas")
                .bodyValue(cuenta)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Account does not exist")))
                .bodyToMono(CuentaDTO.class);
    }

    public Mono<Transferencia> saveTransferencia(Transferencia transaccion) {
        return webClientBuilder
                .build()
                .post()
                .uri(transaccionesServiceUrl + "/api/transfer/create")
                .bodyValue(transaccion)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Transaccion no realziada")))
                .bodyToMono(Transferencia.class);
    }
}
