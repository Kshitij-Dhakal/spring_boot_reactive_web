package com.example.demo.api.controller;

import com.example.demo.api.model.JournalModel;
import com.example.demo.entity.User;
import com.example.demo.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @PostMapping("/new")
    public Mono<?> save(@RequestBody JournalModel journalModel) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .flatMap(o -> {
                    User user = (User) o;
                    return journalService.save(user, journalModel);
                });
    }
}
