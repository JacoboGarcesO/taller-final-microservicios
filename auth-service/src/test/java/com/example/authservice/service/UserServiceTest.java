package com.example.authservice.service;

import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.model.User;
import com.example.authservice.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserServiceTest {

    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserServiceTest() {
        repository = Mockito.mock(IUserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(repository, passwordEncoder);
    }

    @Test
    void register_ShouldSaveUser_WhenUsernameDoesNotExist() {
        RegisterRequest request = new RegisterRequest("luisk", "secret123", null);
        User savedUser = new User(
                "1",
                "luisk",
                "encodedPassword",
                "ROLE_ADMIN"
        );

        Mockito.when(repository.findByUsername(Mockito.anyString()))
                .thenReturn(Mono.empty());
        Mockito.when(passwordEncoder.encode(Mockito.anyString()))
                .thenReturn("encodedPassword");
        Mockito.when(repository.save(Mockito.any(User.class)))
                .thenReturn(Mono.just(savedUser));

        StepVerifier.create(userService.register(request))
                .expectNext(savedUser)
                .verifyComplete();

        Mockito.verify(repository).findByUsername(Mockito.anyString());
        Mockito.verify(passwordEncoder).encode(Mockito.anyString());
        Mockito.verify(repository).save(Mockito.any(User.class));
    }

    @Test
    void register_ShouldReturnError_WhenUserAlreadyExists() {
        RegisterRequest request = new RegisterRequest("luisk", "secret123", null);
        User existingUser = new User("1", "luisk", null, null);

        Mockito.when(repository.findByUsername(Mockito.anyString()))
                .thenReturn(Mono.just(existingUser));

        StepVerifier.create(userService.register(request))
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalStateException && throwable.getMessage().equals("The user already exists"))
                .verify();

        Mockito.verify(repository).findByUsername(Mockito.anyString());
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(User.class));
        Mockito.verify(passwordEncoder, Mockito.never()).encode(Mockito.anyString());
    }
}
