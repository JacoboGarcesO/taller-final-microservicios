package com.axceldev.authenticationservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.axceldev.authenticationservice.model.User;
import com.axceldev.authenticationservice.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class UserDetailServiceTest {

    private IUserRepository userRepository;
    private UserDetailService userDetailService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(IUserRepository.class);
        userDetailService = new UserDetailService(userRepository);
    }

    @Test
    void findByUsername_existingUser_returnsUserDetails() {
        // Arrange
        String username = "testuser";
        User testUser = User.builder()
                .id("12345")
                .username(username)
                .password("password123")
                .role("USER")
                .build();
        when(userRepository.findByUsername(username)).thenReturn(Mono.just(testUser));

        // Act & Assert
        StepVerifier.create(userDetailService.findByUsername(username))
                .expectNextMatches(userDetails -> {
                    assertEquals(username, userDetails.getUsername());
                    assertEquals("password123", userDetails.getPassword());
                    return true;
                })
                .verifyComplete();

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void findByUsername_nonExistingUser_returnsEmptyMono() {
        // Arrange
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(userDetailService.findByUsername(username))
                .expectNextCount(0)
                .verifyComplete();

        verify(userRepository, times(1)).findByUsername(username);
    }
}