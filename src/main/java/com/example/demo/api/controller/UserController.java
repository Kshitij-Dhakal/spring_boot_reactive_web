package com.example.demo.api.controller;

import com.example.demo.api.model.SignUpRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("signup")
    public Mono<?> signUp(@RequestBody final SignUpRequest user) {
        return userService.signUp(user);
    }
}
