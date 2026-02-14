package com.revplay.song;

import com.revplay.config.FileStorageProperties;
import com.revplay.song.dto.SongResponse;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final RpUserRepository userRepository;
    private final FileStorageProperties fileStorageProperties;

    public SongResponse uploadSong(String title,
                                   String genre,
                                   int duration,
                                   LocalDate releaseDate,
                                   MultipartFile audioFile)
            throws IOException {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser artist = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        if (!artist.getRole().name().equals("ARTIST")) {
            throw new RuntimeException("User is not an artist");
        }

        if (audioFile == null || audioFile.isEmpty()) {
            throw new RuntimeException("Audio file is required");
        }

        // Sanitize filename
        String originalName = audioFile.getOriginalFilename();
        String cleanFileName = System.currentTimeMillis() + "_" +
                originalName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        String audioPath = fileStorageProperties.getAudioPath();

        File uploadDir = new File(audioPath);

        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (!created) {
                throw new RuntimeException("Could not create upload directory");
            }
        }

        File destination = new File(uploadDir, cleanFileName);

        audioFile.transferTo(destination);

        Song song = Song.builder()
                .title(title)
                .genre(genre)
                .duration(duration)
                .releaseDate(releaseDate)
                .audioPath(cleanFileName)
                .artist(artist)
                .playCount(0L)
                .build();

        Song saved = songRepository.save(song);

        return SongResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .genre(saved.getGenre())
                .duration(saved.getDuration())
                .artistName(saved.getArtist().getUsername())
                .releaseDate(saved.getReleaseDate())
                .playCount(saved.getPlayCount())
                .build();
    }

    public Resource streamSong(Long songId) {

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        String audioPath = fileStorageProperties.getAudioPath();

        File file = new File(audioPath, song.getAudioPath());

        if (!file.exists()) {
            throw new RuntimeException("Audio file not found on disk");
        }

        song.setPlayCount(song.getPlayCount() + 1);
        songRepository.save(song);

        return new FileSystemResource(file);
    }

    public Page<SongResponse> getAllSongs(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Song> songPage = songRepository.findAll(pageable);

        return songPage.map(song ->
                SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .genre(song.getGenre())
                        .duration(song.getDuration())
                        .artistName(song.getArtist().getUsername())
                        .releaseDate(song.getReleaseDate())
                        .playCount(song.getPlayCount())
                        .build()
        );
    }

    public Page<SongResponse> searchSongs(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Song> songPage =
                songRepository.findByTitleContainingIgnoreCase(keyword, pageable);

        return songPage.map(song ->
                SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .genre(song.getGenre())
                        .duration(song.getDuration())
                        .artistName(song.getArtist().getUsername())
                        .releaseDate(song.getReleaseDate())
                        .playCount(song.getPlayCount())
                        .build()
        );
    }

    public Page<SongResponse> filterSongs(
            String genre,
            String artist,
            Integer releaseYear,
            int page,
            int size,
            String sort
    ) {

        Sort sorting = Sort.by("id").ascending();

        if ("popular".equalsIgnoreCase(sort)) {
            sorting = Sort.by("playCount").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sorting);

        Specification<Song> specification =
                SongSpecification.filterBy(genre, artist, releaseYear);

        Page<Song> songPage =
                songRepository.findAll(specification, pageable);

        return songPage.map(song ->
                SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .genre(song.getGenre())
                        .duration(song.getDuration())
                        .artistName(song.getArtist().getUsername())
                        .releaseDate(song.getReleaseDate())
                        .playCount(song.getPlayCount())
                        .build()
        );
    }

}