package com.revplay.auth.dto;

import com.revplay.user.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
    private Role role;   // USER or ARTIST
}