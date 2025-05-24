package com.example.apigateway.config;

import com.example.apigateway.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

  private final JwtAuthenticationFilter filter;

  @Bean
  public RouteLocator createRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("auth-ms-route", route -> route
                    .path("/api/auth/**")
                    .uri("http://localhost:8081"))
            .route("accounts-ms-route", route -> route
                    .path("/api/cuentas/**")
                    .filters(f -> f.filter(filter))
                    .uri("http://localhost:8082"))
            .route("banks-ms-route", route -> route
                    .path("/api/bank/**")
                    .filters(f -> f.filter(filter))
                    .uri("http://localhost:8083"))
            .route("transfer-ms-route", route -> route
                    .path("/api/transfer/**")
                    .filters(f -> f.filter(filter))
                    .uri("http://localhost:8085"))
            .build();
  }
}
