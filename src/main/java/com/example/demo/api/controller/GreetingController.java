package com.example.demo.api.controller;

import com.example.demo.entity.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class GreetingController {
    @GetMapping
    public Mono<Greeting> greetingMono() {
        return Mono.just(new Greeting("Hello world"));
    }
}
