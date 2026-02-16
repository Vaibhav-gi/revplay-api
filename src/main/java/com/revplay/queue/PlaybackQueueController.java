package com.revplay.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class PlaybackQueueController {

    private final PlaybackQueueService queueService;

    @PostMapping("/{songId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToQueue(
            @PathVariable Long songId) {

        queueService.addToQueue(songId);
        return ResponseEntity.ok("Song added to queue");
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getQueue() {
        return ResponseEntity.ok(queueService.getQueue());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> clearQueue() {
        queueService.clearQueue();
        return ResponseEntity.ok("Queue cleared");
    }
}