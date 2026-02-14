package com.revplay.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String message;
    private Long userId;
    private String username;
    private String role;
}
