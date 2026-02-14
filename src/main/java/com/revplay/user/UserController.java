package com.revplay.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER')")
    public String userDashboard() {
        return "User Dashboard";
    }
}
