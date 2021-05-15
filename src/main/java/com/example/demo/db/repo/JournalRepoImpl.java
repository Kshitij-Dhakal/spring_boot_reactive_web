package com.example.demo.db.repo;

import com.example.demo.api.model.Page;
import com.example.demo.core.exceptions.FailedException;
import com.example.demo.core.repo.AbstractSqlRepo;
import com.example.demo.db.mapper.JournalMapper;
import com.example.demo.entity.Journal;
import com.example.demo.entity.PageRequest;
import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JournalRepoImpl extends AbstractSqlRepo implements JournalRepo {

    private final DatabaseClient client;

    @Override
    public Mono<Journal> save(User user, Journal journal) {
        log.info("Saving journal for user. User id : {}", user.getId());
        final var query = "INSERT INTO journal (id, user_id, content, created, updated) VALUES(:id, :userId, :content, :created, :updated)";
        return client.sql(query)
                .bind("id", journal.getId())
                .bind("userId", user.getId())
                .bind("content", journal.getContent())
                .bind("created", journal.getCreated())
                .bind("updated", journal.getUpdated())
                .fetch()
                .rowsUpdated()
                .flatMap(rowsUpdated -> {
                    if (rowsUpdated == 1)
                        return findById(journal.getId());
                    else
                        return Mono.error(new FailedException("Failed to save journal"));
                });
    }

    @Override
    public Mono<Journal> update(Journal journal) {
        log.info("Updating journal. Journal id : {}", journal.getId());
        final var query = "UPDATE journal SET content=:content, updated=:updated WHERE id=:id";
        return client.sql(query)
                .bind("content", journal.getContent())
                .bind("updated", journal.getUpdated())
                .bind("id", journal.getId())
                .fetch()
                .rowsUpdated()
                .flatMap(rowsUpdated -> {
                    if (rowsUpdated == 1)
                        return findById(journal.getId());
                    else
                        return Mono.error(new FailedException("Failed to update journal"));
                });
    }

    @Override
    public Mono<Journal> findById(String id) {
        log.info("Getting journal by id. Id : {}", id);
        final var query = "SELECT id, user_id, content, created, updated FROM journal WHERE id=:id";
        return client.sql(query)
                .bind("id", id)
                .map(new JournalMapper())
                .first();
    }

    @Override
    public Mono<Page<Journal>> findByUser(User user, PageRequest pageRequest) {
        log.info("Getting journals for user. User id : {}", user.getId());
        final var countQuery = "SELECT COUNT(*) FROM journal WHERE user_id=:userId";
        final var query = getOrderByQuery(pageRequest, "SELECT id, user_id, content, created, updated FROM journal WHERE user_id=:userId ORDER BY %s %s LIMIT :limit OFFSET :offset");
        Mono<Long> count = client.sql(countQuery)
                .bind("userId", user.getId())
                .map(count())
                .first();
        return client.sql(query)
                .bind("userId", user.getId())
                .bind("limit", pageRequest.getPageSize())
                .bind("offset", pageRequest.getPageNumber() * pageRequest.getPageSize())
                .map(new JournalMapper())
                .all()
                .collectList()
                .flatMap(journals ->
                        count.map(total -> new Page<Journal>()
                                .setPageNumber(pageRequest.getPageNumber())
                                .setPageSize(pageRequest.getPageSize())
                                .setTotal(total)
                                .setData(journals)
                        )
                );
    }

    @Override
    public Mono<Boolean> delete(String id) {
        log.info("Deleting journal. Journal id : {}", id);
        final var query = "DELETE FROM journal WHERE id=:id";
        return client.sql(query)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .map(integer -> integer == 1);
    }

}
