package com.revplay.history;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class ListeningHistoryController {

    private final ListeningHistoryService historyService;

    @GetMapping("/recent")
    @PreAuthorize("hasRole('USER') or hasRole('ARTIST')")
    public ResponseEntity<?> getRecentHistory() {
        return ResponseEntity.ok(historyService.getRecentHistory());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ARTIST')")
    public ResponseEntity<?> getFullHistory() {
        return ResponseEntity.ok(historyService.getFullHistory());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('ARTIST')")
    public ResponseEntity<?> clearHistory() {
        historyService.clearHistory();
        return ResponseEntity.ok("History cleared");
    }
}