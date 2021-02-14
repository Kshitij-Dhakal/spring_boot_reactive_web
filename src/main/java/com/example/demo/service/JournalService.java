package com.example.demo.service;

import com.example.demo.api.model.JournalModel;
import com.example.demo.api.model.Page;
import com.example.demo.core.exceptions.FailedException;
import com.example.demo.core.exceptions.InvalidRequestException;
import com.example.demo.core.exceptions.NoDataFoundException;
import com.example.demo.entity.Journal;
import com.example.demo.entity.PageRequest;
import com.example.demo.entity.User;
import com.example.demo.repo.JournalRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.demo.core.utility.Lang.isBlank;
import static com.example.demo.core.utility.Lang.sanitizeDescription;
import static com.example.demo.core.utility.TimeUtility.nanos;
import static com.example.demo.core.utility.Utility.uuid;

@Service
@Slf4j
@RequiredArgsConstructor
public class JournalService {
    private final JournalRepo journalRepo;

    public Mono<Journal> save(User user, JournalModel journalModel) {
        log.info("Saving journal. User id : {}", user.getId());
        Journal journal = journalModel.toJournal();
        String content = sanitizeDescription(journal.getContent());
        if (isBlank(content)) {
            return Mono.error(new InvalidRequestException("Invalid content"));
        }
        Journal build = journal.toBuilder()
                .id(uuid())
                .content(content)
                .created(nanos())
                .updated(nanos())
                .build();
        return journalRepo.save(user, build);
    }

    public Mono<? extends Journal> update(User user, JournalModel journalModel) {
        String id = journalModel.getId();
        log.info("Updating journal. Journal id : {}", id);
        return findById(user, id)
                .flatMap(__ -> {
                    String content = sanitizeDescription(journalModel.getContent());
                    if (isBlank(content)) {
                        return Mono.error(new InvalidRequestException("Invalid content"));
                    }
                    Journal journal = journalModel.toJournal();
                    Journal build = journal.toBuilder()
                            .content(content)
                            .updated(nanos())
                            .build();
                    return journalRepo.update(build);
                })
                .cast(Journal.class);
    }

    public Mono<Page<Journal>> findByUser(User user, PageRequest pageRequest) {
        log.info("Getting my journal. User id : {}", user.getId());
        return journalRepo.findByUser(user, pageRequest);
    }

    public Mono<Journal> findById(final User user, final String id) {
        log.info("Getting journal. Journal id : {}", id);
        return journalRepo.findById(id)
                .filter(dbJournal -> dbJournal.getUserId().equals(user.getId()))
                .switchIfEmpty(Mono.error(new NoDataFoundException("Journal not found")));
    }

    public Mono<Boolean> delete(final User user, final String id) {
        log.info("Getting journal. Journal id : {}", id);
        return findById(user, id)
                .flatMap(__ -> journalRepo.delete(id))
                .map(success -> {
                    if (!success)
                        return Mono.error(new FailedException("Failed to delete journal"));
                    return success;
                })
                .cast(Boolean.class);
    }
}
