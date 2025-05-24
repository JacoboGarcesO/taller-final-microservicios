package com.example.apigateway.config;

import com.example.apigateway.security.JwtAuthenticationFilter;
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
      .route("banco-service-route", route -> route
        .path("/api/bancos/**")
        .filters(f -> f.filters(filter))
        .uri("http://localhost:8081"))
      .route("cuentas-service-route", route -> route
        .path("/api/cuentas/**")
        .filters(f -> f.filters(filter))
        .uri("http://localhost:8082"))
      .route("transaccion-service-route", route -> route
         .path("/api/transaccion/**")
         .filters(f -> f.filters(filter))
         .uri("http://localhost:8083"))
       .route("transferencia-service-route", route -> route
               .path("/api/transferecia/**")
               .filters(f -> f.filters(filter))
               .uri("http://localhost:8084"))
      .route("auth-service-route", route -> route
              .path("/api/auth/**")
              .uri("http://localhost:8085"))
       .build();
  }
}
