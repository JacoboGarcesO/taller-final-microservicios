package com.example.authservice.service;

import com.example.authservice.model.User;
import com.example.authservice.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserDetailServiceTest {

    private final IUserRepository repository;
    private final UserDetailService userDetailService;

    public UserDetailServiceTest() {
        repository = Mockito.mock(IUserRepository.class);
        userDetailService = new UserDetailService(repository);
    }

    @Test
    void findByUsername_ShouldReturnUserDetails_WhenUserExists() {
        User user = new User(
                "1",
                "luisk",
                "encodedPassword",
                "ROLE_ADMIN"
        );

        Mockito.when(repository.findByUsername(Mockito.anyString()))
                .thenReturn(Mono.just(user));

        StepVerifier.create(userDetailService.findByUsername("luisk"))
                .expectNextMatches(userDetails ->
                        userDetails.getUsername().equals("luisk") &&
                                userDetails.getPassword().equals("encodedPassword") &&
                                userDetails.getAuthorities().stream()
                                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")))
                .verifyComplete();

        Mockito.verify(repository).findByUsername(Mockito.anyString());
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUserDoesNotExist() {

        Mockito.when(repository.findByUsername(Mockito.anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(userDetailService.findByUsername("ghost"))
                .verifyComplete();

        Mockito.verify(repository).findByUsername(Mockito.anyString());
    }
}
