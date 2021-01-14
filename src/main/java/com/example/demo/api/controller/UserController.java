package com.example.demo.api.controller;

import com.example.demo.api.model.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    @Autowired
    private UserDetailService userService;

    @PostMapping("signup")
    public Mono<?> signUp(@RequestBody User user) {
        return userService.signUp(user);
    }
}
