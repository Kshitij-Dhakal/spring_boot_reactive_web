package com.example.demo.service;

import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailService implements ReactiveUserDetailsService {
    private final UserService userDetailService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userDetailService.findByEmail(username).map(SecurityUserDetails::new);
    }

    private static class SecurityUserDetails extends User implements UserDetails {
        public SecurityUserDetails(User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            //noinspection unchecked
            return Collections.EMPTY_LIST;
        }

        @Override
        public String getUsername() {
            return this.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
