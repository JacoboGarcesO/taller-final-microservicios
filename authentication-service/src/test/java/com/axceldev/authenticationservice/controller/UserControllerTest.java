package com.axceldev.authenticationservice.controller;

import com.axceldev.authenticationservice.dto.AuthRequest;
import com.axceldev.authenticationservice.dto.RegisterRequest;
import com.axceldev.authenticationservice.service.JwtService;
import com.axceldev.authenticationservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import reactor.test.StepVerifier;

class UserControllerTest {

    private UserService userService;
    private ReactiveAuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        authenticationManager = mock(ReactiveAuthenticationManager.class);
        jwtService = mock(JwtService.class);
        userController = new UserController(userService, authenticationManager, jwtService);
    }

    @Test
    void singUpUser_success() {
        RegisterRequest request = new RegisterRequest("axcelDev", "12123", "ADMIN");
        when(userService.registerUser(request)).thenReturn(Mono.just("Usuario registrado"));

        StepVerifier.create(userController.singUpUser(request))
                .expectNext("Usuario registrado")
                .verifyComplete();
    }

    @Test
    void singUpUser_userAlreadyExists() {
        RegisterRequest request = new RegisterRequest("axcelDev", "12123", "ADMIN");
        when(userService.registerUser(request)).thenReturn(Mono.empty());

        StepVerifier.create(userController.singUpUser(request))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("User already exists"))
                .verify();
    }

    @Test
    void signInUser_success() {
        AuthRequest authRequest = mock(AuthRequest.class);
        when(authRequest.username()).thenReturn("user");
        when(authRequest.password()).thenReturn("pass");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(authentication));
        when(jwtService.generateToken(authentication)).thenReturn("jwt-token");

        StepVerifier.create(userController.signInUser(authRequest))
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.OK &&
                                response.getBody() != null &&
                                "jwt-token".equals(response.getBody().token()))
                .verifyComplete();
    }

    @Test
    void signInUser_unauthorized() {
        AuthRequest authRequest = mock(AuthRequest.class);
        when(authRequest.username()).thenReturn("user");
        when(authRequest.password()).thenReturn("wrongpass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(userController.signInUser(authRequest))
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.UNAUTHORIZED)
                .verifyComplete();
    }

}