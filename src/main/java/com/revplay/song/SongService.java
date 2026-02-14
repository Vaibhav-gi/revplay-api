package com.revplay.song;

import com.revplay.config.FileStorageProperties;
import com.revplay.song.dto.SongResponse;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
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

        // 1️⃣ Get logged-in user
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser artist = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        if (!artist.getRole().name().equals("ARTIST")) {
            throw new RuntimeException("User is not an artist");
        }

        // 2️⃣ Validate file
        if (audioFile == null || audioFile.isEmpty()) {
            throw new RuntimeException("Audio file is required");
        }

        // 3️⃣ Generate unique filename
        String fileName = System.currentTimeMillis() + "_" +
                audioFile.getOriginalFilename();

        // 4️⃣ Get upload directory from properties
        String audioPath = fileStorageProperties.getAudioPath();

        File uploadDir = new File(audioPath);

        // 5️⃣ Create directory if it doesn't exist
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (!created) {
                throw new RuntimeException("Could not create upload directory");
            }
        }

        // 6️⃣ Create destination file safely
        File destination = new File(uploadDir, fileName);

        // 7️⃣ Save file
        audioFile.transferTo(destination);

        // 8️⃣ Save song entity
        Song song = Song.builder()
                .title(title)
                .genre(genre)
                .duration(duration)
                .releaseDate(releaseDate)
                .audioPath(fileName)
                .artist(artist)
                .playCount(0L)
                .build();

        Song saved = songRepository.save(song);

        // 9️⃣ Return DTO
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
}