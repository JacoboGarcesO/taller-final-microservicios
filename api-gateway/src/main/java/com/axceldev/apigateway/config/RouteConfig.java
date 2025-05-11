package com.axceldev.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator createRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("banks-service-route", r -> r.path("/api/banks/**")
                        .uri("http://localhost:8081"))
                .route("accounts-service-route", r -> r.path("/api/accounts/**")
                        .uri("http://localhost:8082"))
                .route("transactions-service-route", r -> r.path("/api/transactions/**")
                        .uri("http://localhost:8083"))
                .route("transfers-service-route", r -> r.path("/api/transfers/**")
                        .uri("http://localhost:8084"))
                .build();
    }
}