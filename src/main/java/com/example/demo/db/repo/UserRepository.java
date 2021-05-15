package com.example.demo.db.repo;

import com.example.demo.entity.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(User user);

    Mono<User> findById(String id);

    Mono<User> findByEmail(String email);
}
