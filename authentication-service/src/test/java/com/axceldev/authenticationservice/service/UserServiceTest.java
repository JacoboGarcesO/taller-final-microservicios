package com.axceldev.authenticationservice.service;

import com.axceldev.authenticationservice.dto.RegisterRequest;
import com.axceldev.authenticationservice.model.User;
import com.axceldev.authenticationservice.repository.IUserRepository;
import com.axceldev.authenticationservice.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should register a new user successfully when username is unique")
    void registerUser_WhenUsernameIsUnique_Success() {
        String username = "uniqueUser";
        String password = "password123";
        String role = "USER";
        String encodedPassword = "encodedPassword";

        RegisterRequest request = new RegisterRequest(username, password, role);
        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .role(role)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Mono.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        Mono<Object> result = userService.registerUser(request);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should throw an error when username already exists")
    void registerUser_WhenUsernameExists_Error() {
        String username = "existingUser";
        String password = "password123";
        String role = "USER";

        RegisterRequest request = new RegisterRequest(username, password, role);
        User existingUser = User.builder()
                .username(username)
                .password("existingEncodedPassword")
                .role(role)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Mono.just(existingUser));

        Mono<Object> result = userService.registerUser(request);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException
                                && throwable.getMessage().equals("User already exists"))
                .verify();

        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, never()).encode(password);
        verify(userRepository, never()).save(any(User.class));
    }
}