package com.axceldev.authenticationservice.repository;

import com.axceldev.authenticationservice.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IUserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
