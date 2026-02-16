package com.revplay.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlaybackSettingsController {

    private final PlaybackSettingsService service;

    @GetMapping("/settings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getSettings() {
        return ResponseEntity.ok(service.getSettings());
    }

    @PostMapping("/shuffle")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> toggleShuffle() {
        service.toggleShuffle();
        return ResponseEntity.ok("Shuffle toggled");
    }

    @PostMapping("/repeat")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> setRepeat(
            @RequestParam RepeatMode mode) {

        service.setRepeatMode(mode);
        return ResponseEntity.ok("Repeat mode updated");
    }
}
