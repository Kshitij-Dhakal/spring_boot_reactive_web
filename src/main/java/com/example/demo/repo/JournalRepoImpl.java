package com.example.demo.repo;

import com.example.demo.api.model.Page;
import com.example.demo.entity.Journal;
import com.example.demo.entity.PageRequest;
import com.example.demo.entity.User;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.example.demo.api.model.Page.map;
import static com.example.demo.core.repo.SqlRepo.count;
import static com.example.demo.core.repo.SqlRepo.getQuery;

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
    public Mono<Page<?>> findByUser(User user, PageRequest pageRequest) {
        final var countQuery = "SELECT COUNT(*) FROM journal WHERE user_id=:userId";
        final var query = getQuery(pageRequest, "SELECT id, user_id, content, created, updated FROM journal WHERE user_id=:userId ORDER BY created %s LIMIT :limit OFFSET :offset");
        Mono<Long> count = client.sql(countQuery)
                .bind("userId", user.getId())
                .map(count())
                .first();
        return client.sql(query)
                .bind("userId", user.getId())
                .bind("limit", pageRequest.getPageSize())
                .bind("offset", pageRequest.getPageNumber())
                .map(JournalRowMapper())
                .all()
                .collectList()
                .flatMap(journals ->
                        count.map(total -> Page.builder()
                                .pageNumber(pageRequest.getPageNumber())
                                .pageSize(pageRequest.getPageSize())
                                .total(total)
                                .data(map(journals))
                                .build()));
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
