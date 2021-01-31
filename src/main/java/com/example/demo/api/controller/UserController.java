package com.example.demo.api.controller;

import com.example.demo.api.model.SignUpRequest;
import com.example.demo.api.model.UserModel;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    public Mono<UserModel> signUp(@RequestBody final SignUpRequest user) {
        return userService.signUp(user);
    }
}
