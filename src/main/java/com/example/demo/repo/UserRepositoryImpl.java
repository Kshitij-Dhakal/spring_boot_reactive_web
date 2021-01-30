package com.example.demo.repo;

import com.example.demo.entity.User;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Function;

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
                .map(userRowMapper())
                .first();
    }

    @Override
    public Mono<User> findByEmail(String email) {
        log.info("Getting user by email. Email : {}", email);
        final var query = "SELECT id, full_name, email, password, created, updated FROM person WHERE email=:email";
        return client.sql(query)
                .bind("email", email)
                .map(userRowMapper())
                .first();
    }

    @NotNull
    private Function<Row, User> userRowMapper() {
        return row -> User.builder()
                .id((String) row.get("id"))
                .fullName((String) row.get("full_name"))
                .email((String) row.get("email"))
                .password((String) row.get("password"))
                .created((Long) row.get("created"))
                .updated((Long) row.get("updated"))
                .build();
    }
}
