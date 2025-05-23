package com.axceldev.apigateway.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import static org.junit.jupiter.api.Assertions.*;

class RouteConfigTest {

    @Test
    void testCreateRouteLocator() {
        RouteLocatorBuilder builder = Mockito.mock(RouteLocatorBuilder.class);
        RouteLocatorBuilder.Builder routesBuilder = Mockito.mock(RouteLocatorBuilder.Builder.class);
        Mockito.when(builder.routes()).thenReturn(routesBuilder);
        Mockito.when(routesBuilder.route(Mockito.anyString(), Mockito.any())).thenReturn(routesBuilder);
        Mockito.when(routesBuilder.build()).thenReturn(Mockito.mock(RouteLocator.class));
        RouteConfig routeConfig = new RouteConfig();
        RouteLocator routeLocator = routeConfig.createRouteLocator(builder);
        assertNotNull(routeLocator, "El RouteLocator no debe ser nulo");
        Mockito.verify(builder).routes();
        Mockito.verify(routesBuilder, Mockito.times(4)).route(Mockito.anyString(), Mockito.any());
        Mockito.verify(routesBuilder).build();
    }

}