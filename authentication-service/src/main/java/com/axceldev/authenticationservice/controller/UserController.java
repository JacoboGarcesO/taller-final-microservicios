package com.axceldev.authenticationservice.controller;

import com.axceldev.authenticationservice.dto.AuthRequest;
import com.axceldev.authenticationservice.dto.AuthResponse;
import com.axceldev.authenticationservice.dto.RegisterRequest;
import com.axceldev.authenticationservice.service.JwtService;
import com.axceldev.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/sign-up")
    public Mono<Object> singUpUser(@RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest)
                .switchIfEmpty(Mono.error(new RuntimeException("User already exists")));
    }

    @PostMapping("/sign-in")
    public Mono<ResponseEntity<AuthResponse>> signInUser(@RequestBody AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());

        return authenticationManager.authenticate(authenticationToken)
                .map(authentication -> {
                    String jwtToken = jwtService.generateToken(authentication);
                    return ResponseEntity.ok(new AuthResponse(jwtToken));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

}
