package com.revplay.artist;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ARTIST')")
    public String artistDashboard() {
        return "Artist Dashboard";
    }
}
