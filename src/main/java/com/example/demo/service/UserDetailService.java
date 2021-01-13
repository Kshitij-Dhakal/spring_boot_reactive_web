package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserDetailService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<User> signUp(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(u -> {
                    if (u != null) {
                        throw new DuplicateKeyException("Email already exists");
                    }
                    User build = User.builder()
                            .id(UUID.randomUUID().toString())
                            .fullName(user.getFullName())
                            .email(user.getEmail())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .created(System.nanoTime())
                            .updated(System.nanoTime())
                            .build();
                    return userRepository.save(build);
                });
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
