package com.revplay.song;

import com.revplay.song.dto.SongResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @GetMapping("/stream/{id}")
    public ResponseEntity<Resource> streamSong(@PathVariable Long id) {

        Resource resource = songService.streamSong(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<Page<SongResponse>> getAllSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<SongResponse> songs =
                songService.getAllSongs(page, size);

        return ResponseEntity.ok(songs);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SongResponse>> searchSongs(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<SongResponse> songs =
                songService.searchSongs(keyword, page, size);

        return ResponseEntity.ok(songs);
    }

}
