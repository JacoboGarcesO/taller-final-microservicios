package com.example.authservice.service;

import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.model.User;
import com.example.authservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Mono<Object> register(RegisterRequest request) {
        return repository.findByUsername(request.username())
                .flatMap(existing -> Mono.error(new IllegalStateException("The user already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    User nuevo = new User();
                    nuevo.setUsername(request.username());
                    nuevo.setPassword(passwordEncoder.encode(request.password()));
                    nuevo.setRole(Optional.ofNullable(request.role()).orElse("ROLE_USER"));
                    return repository.save(nuevo);
                }));
    }

}
