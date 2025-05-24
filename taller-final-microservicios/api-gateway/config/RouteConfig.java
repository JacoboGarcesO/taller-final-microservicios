package com.apigateway.config;

import com.apigateway.security.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RouteConfig {
  private final JwtAuthenticationFilter filter;

  public RouteConfig(JwtAuthenticationFilter filter) {
    this.filter = filter;
  }

  @Bean
  public RouteLocator createRouteLocator(RouteLocatorBuilder builder) {

    return builder.routes()
            .route("auth-service", route -> route .path("/api/auth/**")
                    .filters(f -> f.filter(filter))
                    .uri("http://localhost:8081"))
            .route("bank-service", r -> r.path("/api/banks/**")
                    .uri("http://localhost:8082"))
            .route("account-service", r -> r.path("/api/accounts/**")
                    .uri("http://localhost:8083"))
            .route("account-service", r -> r.path("/api/transactions/**")
                    .uri("http://localhost:8084"))
            .route("transfers-service-route", r -> r.path("/api/transfers/**")
                    .uri("http://localhost:8084"))
            .build(); //build() - construir la ruta


  }
}
