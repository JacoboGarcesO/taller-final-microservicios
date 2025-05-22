package com.axceldev.accountservice.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class WebClientConfig {

    @Value("${app.bank.base-url}")
    private String baseUrl;

    @Value("${app.bank.timeout}")
    private int timeout;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .clientConnector(connector())
                .build();
    }

    private ClientHttpConnector connector() {

        return new ReactorClientHttpConnector(HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE)
                .option(CONNECT_TIMEOUT_MILLIS, timeout)
                .compress(true)
                .keepAlive(true)
                .doOnConnected(connection -> connection
                        .addHandlerLast(new ReadTimeoutHandler(timeout, MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(timeout, MILLISECONDS))));
    }

}
