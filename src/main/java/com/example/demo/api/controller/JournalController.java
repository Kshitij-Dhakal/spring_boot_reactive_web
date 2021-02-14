package com.example.demo.api.controller;

import com.example.demo.api.model.JournalModel;
import com.example.demo.entity.Journal;
import com.example.demo.entity.PageRequest;
import com.example.demo.enums.Order;
import com.example.demo.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.example.demo.api.controller.ControllerUtility.authorized;
import static com.example.demo.api.controller.ControllerUtility.deleted;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;

    @PostMapping
    public Mono<Journal> save(@RequestBody final JournalModel journalModel) {
        return authorized(user -> journalService.save(user, journalModel))
                .cast(Journal.class);
    }

    @PutMapping
    public Mono<Journal> update(@RequestBody final JournalModel journalModel) {
        return authorized(user -> journalService.update(user, journalModel))
                .cast(Journal.class);
    }

    @GetMapping("/my")
    public Mono<?> getMyJournal(@RequestParam(value = "pageNumber", defaultValue = "0") final long pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = "10") final long pageSize,
                                @RequestParam(value = "order", defaultValue = "DESC") final Order order,
                                @RequestParam(value = "orderBy", defaultValue = "created") final String sortBy) {
        return authorized(user -> journalService.findByUser(user, PageRequest.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .order(order)
                .orderBy(sortBy)
                .build()));
    }

    @GetMapping("/{id}")
    public Mono<Journal> findById(@PathVariable("id") final String id) {
        return authorized(user -> journalService.findById(user, id))
                .cast(Journal.class);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> delete(@PathVariable("id") final String id) {
        return authorized(user -> journalService.delete(user, id))
                .map(__ -> deleted());
    }
}
