package com.example.authservice.controller;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.dto.AuthResponse;
import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.service.JwtService;
import com.example.authservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

class AuthControllerTest {

    private final JwtService jwtService;
    private final ReactiveAuthenticationManager authManager;
    private final UserService userService;
    private final WebTestClient webTestClient;


    public AuthControllerTest() {
        jwtService = Mockito.mock(JwtService.class);
        authManager = Mockito.mock(ReactiveAuthenticationManager.class);
        userService = Mockito.mock(UserService.class);
        webTestClient = WebTestClient.bindToController(new AuthController(jwtService, authManager, userService)).build();
    }

    @Test
    void login_ShouldReturnAuthResponse_WhenCredentialsAreValid() {

        AuthRequest request = new AuthRequest("user", "password");
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(authentication));

        Mockito.when(jwtService.generateToken(authentication))
                .thenReturn("mocked-jwt-token");

        webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponse.class)
                .value(authResponse -> {
                    Assertions.assertNotNull(authResponse);
                    Assertions.assertEquals("mocked-jwt-token", authResponse.token());
                });

        Mockito.verify(authManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtService).generateToken(authentication);
    }

    @Test
    void login_ShouldReturnUnauthorized_WhenCredentialsAreInvalid() {
        AuthRequest request = new AuthRequest("user", "wrongpassword");

        Mockito.when(authManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized();

        Mockito.verify(authManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verifyNoInteractions(jwtService);
    }

    @Test
    void register_ShouldReturnUserServiceResponse() {

        RegisterRequest request = new RegisterRequest("user", "user123", "luis@gmail.com");

        String mockResponse = "User registered successfully";

        Mockito.when(userService.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(Mono.just(mockResponse));

        webTestClient.post()
                .uri("/api/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    Assertions.assertNotNull(response);
                    Assertions.assertEquals("\"User registered successfully\"", response);
                });

        Mockito.verify(userService).register(Mockito.any(RegisterRequest.class));
    }

}
