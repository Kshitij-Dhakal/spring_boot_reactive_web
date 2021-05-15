package com.example.demo.service;

import com.example.demo.api.model.SignUpRequest;
import com.example.demo.api.model.UserModel;
import com.example.demo.core.exceptions.DuplicateResourceException;
import com.example.demo.core.exceptions.InvalidRequestException;
import com.example.demo.entity.User;
import com.example.demo.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.demo.core.util.StringUtils.isBlank;
import static com.example.demo.core.util.StringUtils.sanitizeText;
import static com.example.demo.core.util.TimeUtils.nanos;
import static com.example.demo.core.util.JournalUtils.uuid;
import static com.example.demo.core.util.Validator.isValidEmail;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserModel> signUp(SignUpRequest signUpRequest) {
        log.info("Signing up. Email : {}", signUpRequest.getEmail());
        User user = signUpRequest.toUser();
        String email = sanitizeText(user.getEmail());
        return findByEmail(email)
                .flatMap(__ -> Mono.error(new DuplicateResourceException("Email already exists")))
                .switchIfEmpty(saveUser(user, email))
                .cast(UserModel.class);
    }

    private Mono<UserModel> saveUser(User user, String email) {
        String fullName = sanitizeText(user.getFullName());
        if (isBlank(fullName) &&
                fullName.length() > 256) {
            return Mono.error(new InvalidRequestException("Invalid user name"));
        }
        if (isBlank(email) ||
                !isValidEmail(email)) {
            return Mono.error(new InvalidRequestException("Invalid email"));
        }
        User build = user.toBuilder()
                .id(uuid())
                .fullName(fullName)
                .email(email)
                .password(passwordEncoder.encode(user.getPassword()))
                .created(nanos())
                .updated(nanos())
                .build();
        return userRepository.save(build)
                .map(UserModel::fromUser);
    }

    public Mono<User> findByEmail(String email) {
        log.info("Getting user by email. Email : {}", email);
        return userRepository.findByEmail(email);
    }

    public Mono<User> findById(String id) {
        log.info("Getting user by id. User id : {}", id);
        return userRepository.findById(id);
    }
}
