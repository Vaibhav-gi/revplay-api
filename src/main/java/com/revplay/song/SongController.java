package com.revplay.song;

import com.revplay.song.dto.SongResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<SongResponse> uploadSong(
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam int duration,
            @RequestParam String releaseDate,
            @RequestParam MultipartFile audioFile
    ) throws IOException {

        SongResponse response = songService.uploadSong(
                title,
                genre,
                duration,
                LocalDate.parse(releaseDate), audioFile
        );

        return ResponseEntity.ok(response);

    }
}
