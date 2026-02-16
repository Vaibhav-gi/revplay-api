package com.revplay.album;

import com.revplay.album.dto.AlbumResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> createAlbum(
            @RequestParam String name,
            @RequestParam(required = false)
            String releaseDate
    ) {
        albumService.createAlbum(name, releaseDate);

        return ResponseEntity.ok("Album created successfully");

    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse>getAlbumbyId(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllAlbums(
            @RequestParam(required = false) String artist,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                albumService.getAllAlbums(artist, page, size)
        );
    }

    @PutMapping("/{albumId}/songs/{songId}")
    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> addSongToAlbum(
            @PathVariable Long albumId,
            @PathVariable Long songId
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: "+auth.getAuthorities());

        System.out.println("Auth name: "+ auth.getName());
        System.out.println("Authorities: "+ auth.getAuthorities());

        albumService.addSongToAlbum(albumId, songId);

        return ResponseEntity.ok("Song added to album successfully");


    }

    @DeleteMapping("/{albumId}")
    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long albumId) {

        albumService.deleteAlbum(albumId);

        return ResponseEntity.ok("Album deleted successfully");
    }

    @PutMapping("/{albumId}")
    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> updateAlbum(
            @PathVariable Long albumId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String releaseDate,
            @RequestParam(required = false) MultipartFile coverImage
    ) throws IOException {

        albumService.updateAlbum(albumId, name, description, releaseDate, coverImage);

        return ResponseEntity.ok("Album updated successfully");
    }
}
