package com.axceldev.authenticationservice.service;

import com.axceldev.authenticationservice.dto.RegisterRequest;
import com.axceldev.authenticationservice.model.User;
import com.axceldev.authenticationservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<Object> registerUser(RegisterRequest request) {
        return userRepository.findByUsername(request.username())
                .flatMap(exitsUser -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    User user = User.builder()
                            .username(request.username())
                            .password(passwordEncoder.encode(request.password()))
                            .role(request.role())
                            .build();
                    return userRepository.save(user);
                }));
    }
}
