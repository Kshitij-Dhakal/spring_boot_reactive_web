package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exceptions.DuplicateResourceException;
import com.example.demo.exceptions.InvalidRequestException;
import com.example.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.example.demo.utility.Lang.isBlank;
import static com.example.demo.utility.Lang.sanitizeText;
import static com.example.demo.utility.Utility.nanos;
import static com.example.demo.utility.Validator.isValidEmail;

@Service
@RequiredArgsConstructor
public class UserDetailService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<?> signUp(User user) {
        String email = sanitizeText(user.getEmail());
        return findByEmail(email)
                .flatMap(__ -> Mono.error(new DuplicateResourceException("Email already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    String fullName = sanitizeText(user.getFullName());
                    if (isBlank(fullName) &&
                            fullName.length() > 256) {
                        return Mono.error(new InvalidRequestException("Invalid user name"));
                    }
                    if (isBlank(email) ||
                            !isValidEmail(email)) {
                        return Mono.error(new InvalidRequestException("Invalid email"));
                    }
                    User build = User.builder()
                            .fullName(fullName)
                            .email(email)
                            .password(passwordEncoder.encode(user.getPassword()))
                            .created(nanos())
                            .updated(nanos())
                            .build();
                    return userRepository.save(build)
                            .map(clearPassword());
                }));
    }

    @NotNull
    private Function<User, User> clearPassword() {
        return u -> {
            u.setPassword("");
            return u;
        };
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
