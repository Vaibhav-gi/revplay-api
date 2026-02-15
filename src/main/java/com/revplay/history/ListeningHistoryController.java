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

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(historyService.getRecentHistory());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> clearHistory() {
        historyService.clearHistory();
        return ResponseEntity.ok("History cleared");
    }
}