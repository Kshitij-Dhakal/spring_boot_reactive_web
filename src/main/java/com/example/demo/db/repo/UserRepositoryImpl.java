package com.example.demo.db.repo;

import com.example.demo.entity.User;
import com.example.demo.db.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final DatabaseClient client;

    @Override
    public Mono<User> save(User user) {
        log.info("Saving user. Email : {}", user.getEmail());
        final var query = "INSERT INTO person (id, full_name, email, password, created, updated) VALUES(:id, :full_name, :email, :password, :created, :updated)";
        return client.sql(query)
                .bind("id", user.getId())
                .bind("full_name", user.getFullName())
                .bind("email", user.getEmail())
                .bind("password", user.getPassword())
                .bind("created", user.getCreated())
                .bind("updated", user.getUpdated())
                .then()
                .then(findById(user.getId()));
    }

    @Override
    public Mono<User> findById(String id) {
        log.info("Getting user by id. User id : {}", id);
        final var query = "SELECT id, full_name, email, password, created, updated FROM person WHERE id=:id";
        return client.sql(query)
                .bind("id", id)
                .map(new UserMapper())
                .first();
    }

    @Override
    public Mono<User> findByEmail(String email) {
        log.info("Getting user by email. Email : {}", email);
        final var query = "SELECT id, full_name, email, password, created, updated FROM person WHERE email=:email";
        return client.sql(query)
                .bind("email", email)
                .map(new UserMapper())
                .first();
    }
}
