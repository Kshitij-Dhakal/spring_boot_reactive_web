package com.example.demo.api.controller;

import com.example.demo.api.model.JournalModel;
import com.example.demo.entity.PageRequest;
import com.example.demo.entity.User;
import com.example.demo.enums.Order;
import com.example.demo.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @PostMapping("/new")
    public Mono<?> save(@RequestBody final JournalModel journalModel) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .flatMap(o -> {
                    User user = (User) o;
                    return journalService.save(user, journalModel);
                });
    }

    @GetMapping("/my")
    public Flux<?> getMyJournal(@RequestParam(value = "pageNumber", defaultValue = "0") final long pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = "10") final long pageSize,
                                @RequestParam(value = "order", defaultValue = "DESC") final Order order,
                                @RequestParam(value = "orderBy", defaultValue = "created") final String sortBy) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .flatMapMany(o -> {
                    User user = (User) o;
                    return journalService.getMyJournal(user, PageRequest.builder()
                            .pageNumber(pageNumber)
                            .pageSize(pageSize)
                            .order(order)
                            .orderBy(sortBy)
                            .build());
                });
    }
}
