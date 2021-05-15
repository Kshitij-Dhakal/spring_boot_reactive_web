package com.example.demo.service;

import com.example.demo.api.model.LoginRequest;
import com.example.demo.core.exceptions.InvalidRequestException;
import com.example.demo.entity.User;
import com.example.demo.db.repo.AuthRepo;
import com.example.demo.db.repo.UserRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.example.demo.core.util.SecurityUtils.secureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtTokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final AuthRepo authRepo;

    public Mono<Pair<String, String>> login(Mono<LoginRequest> authRequest) {
        // create refresh token
        // save refresh token in user id
        // create jwt
        // return jwt and refresh token
        return authRequest
                .flatMap(login -> authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()))
                        .flatMap(getTokenPair())
                );
    }

    private Function<Authentication, Mono<? extends ImmutablePair<String, String>>> getTokenPair() {
        return authentication -> {
            User principal = (User) authentication.getPrincipal();
            String accountId = principal.getId();
            String token = secureRandom(32);
            String jwt = tokenProvider.createToken(authentication);
            return authRepo.save(accountId, token)
                    .map(aLong -> new ImmutablePair<>(jwt, token));
        };
    }

    public Mono<String> refreshAccessToken(String refreshToken, String bearerToken) {
        log.info("Refresh access token request received.");
        // get user id from bearerToken
        // check if refresh token is valid from user id
        // create new bearerToken
        // return new bearerToken
        if (!bearerToken.startsWith("Bearer ")) {
            return Mono.error(new InvalidRequestException("Invalid bearer token"));
        }
        String accessToken = bearerToken.substring(7);
        Authentication auth = tokenProvider.getAuthenticationFromExpiredToken(accessToken);
        User user = (User) auth.getPrincipal();
        String userId = user.getId();
        return authRepo.checkToken(userId, refreshToken)
                .flatMap(aBoolean -> {
                    if (!aBoolean) return Mono.error(new InvalidRequestException("Invalid refresh token"));
                    return Mono.just(tokenProvider.createToken(auth));
                });
    }

    public Mono<Boolean> logout(String accountId, String refreshToken) {
        log.info("Logging out user.");
        // remove refresh token from repo
        return authRepo.removeToken(accountId, refreshToken)
                .map(aLong -> aLong == 1);
    }

}