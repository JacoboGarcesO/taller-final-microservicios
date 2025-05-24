package com.example.authservice.repository;

import com.example.authservice.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String username);
}