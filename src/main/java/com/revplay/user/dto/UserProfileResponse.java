package com.revplay.user.dto;

import com.revplay.user.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserProfileResponse {

    private Long id;
    private String email;
    private String username;
    private String displayName;
    private String bio;
    private String profileImage;
    private Role role;
    private LocalDateTime createdAt;
}
