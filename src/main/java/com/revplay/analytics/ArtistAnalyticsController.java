package com.revplay.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artist/dashboard")
@RequiredArgsConstructor
public class ArtistAnalyticsController {

    private final ArtistAnalyticsService analyticsService;

    @GetMapping
    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> getDashboard() {
        return ResponseEntity.ok(analyticsService.getDashboard());
    }
}

