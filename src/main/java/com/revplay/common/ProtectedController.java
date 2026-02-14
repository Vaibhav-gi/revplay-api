package com.revplay.common;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class ProtectedController {

    @GetMapping("/secure")
    public String secureEndpoint() {
        return "You are authenticated!";
    }
}
