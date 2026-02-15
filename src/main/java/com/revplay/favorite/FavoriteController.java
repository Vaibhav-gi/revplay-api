package com.revplay.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{songId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> addFavorite(@PathVariable Long songId) {

        favoriteService.addFavorite(songId);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/{songId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> removeFavorite(@PathVariable Long songId) {

        favoriteService.removeFavorite(songId);
        return ResponseEntity.ok("Removed from favorites");
    }
}