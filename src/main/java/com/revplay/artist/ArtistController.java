package com.revplay.artist;

import com.revplay.artist.dto.ArtistProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<ArtistProfileResponse>
            getArtistProfile(@PathVariable Long id) {

        return ResponseEntity.ok(artistService.getArtistProfile(id));
    }
}
