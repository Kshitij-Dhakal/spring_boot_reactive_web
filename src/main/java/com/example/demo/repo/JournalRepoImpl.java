package com.example.demo.repo;

import com.example.demo.entity.Journal;
import com.example.demo.entity.User;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class JournalRepoImpl implements JournalRepo {

    private final DatabaseClient client;

    @Override
    public Mono<Journal> save(User user, Journal journal) {
        final var query = "INSERT INTO journal (id, user_id, content, created, updated) VALUES(:id, :userId, :content, :created, :updated)";
        return client.sql(query)
                .bind("id", journal.getId())
                .bind("userId", user.getId())
                .bind("content", journal.getContent())
                .bind("created", journal.getCreated())
                .bind("updated", journal.getUpdated())
                .then()
                .then(findById(journal.getId()));
    }

    @Override
    public Mono<Journal> findById(String id) {
        final var query = "SELECT id, user_id, content, created, updated FROM journal WHERE id=:id";
        return client.sql(query)
                .bind("id", id)
                .map(JournalRowMapper())
                .first();
    }

    @Override
    public Flux<Journal> findByUser(User user) {
        final var query = "SELECT id, user_id, content, created, updated FROM journal WHERE user_id=:userId";
        return client.sql(query)
                .bind("userId", user.getId())
                .map(JournalRowMapper())
                .all();
    }

    @NotNull
    private Function<Row, Journal> JournalRowMapper() {
        return row -> Journal.builder()
                .id((String) row.get("id"))
                .content((String) row.get("content"))
                .created((Long) row.get("created"))
                .updated((Long) row.get("updated"))
                .build();
    }
}
