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
public class UserModel {
    private String id;
    private String fullName;
    private String email;
    private String profilePicture;
    private Long created;
    private Long updated;

    public static UserModel fromUser(User user) {
        return UserModel.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .created(user.getCreated())
                .updated(user.getUpdated())
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .fullName(fullName)
                .email(email)
                .profilePicture(profilePicture)
                .created(created)
                .updated(updated)
                .build();
    }
}
