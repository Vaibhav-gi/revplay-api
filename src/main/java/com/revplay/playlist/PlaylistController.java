package com.revplay.playlist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    // Create playlist
    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam boolean isPublic
    ) {

        Playlist playlist =
                playlistService.createPlaylist(name, description, isPublic);

        return ResponseEntity.ok(playlist);
    }

    // Add song to playlist
    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<String> addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {

        playlistService.addSongToPlaylist(playlistId, songId);

        return ResponseEntity.ok("Song added to playlist");
    }

    // Remove song
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<String> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {

        playlistService.removeSongFromPlaylist(playlistId, songId);

        return ResponseEntity.ok("Song removed from playlist");
    }

    // Get playlist songs (ordered)
    @GetMapping("/{playlistId}")
    public ResponseEntity<List<PlaylistSong>> getPlaylistSongs(
            @PathVariable Long playlistId
    ) {

        return ResponseEntity.ok(
                playlistService.getPlaylistSongs(playlistId)
        );
    }
}