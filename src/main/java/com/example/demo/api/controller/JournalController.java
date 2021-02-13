package com.example.demo.api.controller;

import com.example.demo.api.model.JournalModel;
import com.example.demo.entity.Journal;
import com.example.demo.entity.PageRequest;
import com.example.demo.enums.Order;
import com.example.demo.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;

    @PostMapping
    public Mono<Journal> save(@RequestBody final JournalModel journalModel) {
        return ControllerUtility
                .authorized(user -> journalService.save(user, journalModel))
                .cast(Journal.class);
    }

    public Mono<Journal> update(@RequestBody final JournalModel journalModel) {
        return ControllerUtility
                .authorized(user -> journalService.update(user, journalModel))
                .cast(Journal.class);
    }

    @GetMapping("/my")
    public Mono<?> getMyJournal(@RequestParam(value = "pageNumber", defaultValue = "0") final long pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = "10") final long pageSize,
                                @RequestParam(value = "order", defaultValue = "DESC") final Order order,
                                @RequestParam(value = "orderBy", defaultValue = "created") final String sortBy) {
        return ControllerUtility
                .authorized(user -> journalService.findByUser(user, PageRequest.builder()
                        .pageNumber(pageNumber)
                        .pageSize(pageSize)
                        .order(order)
                        .orderBy(sortBy)
                        .build()));
    }
}
