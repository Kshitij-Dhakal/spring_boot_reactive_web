package com.example.demo.api.model;

import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String profilePicture;
    private Long created;
    private Long updated;

    public User toUser() {
        return User.builder()
                .id(id)
                .fullName(fullName)
                .email(email)
                .password(password)
                .profilePicture(profilePicture)
                .created(created)
                .updated(updated)
                .build();
    }
}
