package com.revplay.album;

import com.revplay.album.dto.AlbumResponse;
import com.revplay.config.FileStorageProperties;
import com.revplay.song.Song;
import com.revplay.song.SongRepository;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final RpUserRepository userRepository;
    private final FileStorageProperties fileStorageProperties;

    public void addSongToAlbum(Long albumId, Long songId) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        RpUser artist = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));

        if (!album.getArtist().getId().equals(artist.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your album");
        }

        if (!song.getArtist().getId().equals(artist.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your song");
        }

        song.setAlbum(album);
        songRepository.save(song);
    }

    @Transactional
    public void createAlbum(String name, String releaseDateStr) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser artist = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        if (!artist.getRole().name().equals("ARTIST")) {
            throw new RuntimeException("User is not an artist");
        }

        LocalDate releaseDate = null;
        if (releaseDateStr != null && !releaseDateStr.isBlank()) {
            releaseDate = LocalDate.parse(releaseDateStr);
        }

        Album album = Album.builder()
                .name(name)
                .releaseDate(releaseDate)
                .artist(artist)
                .build();

        albumRepository.save(album);
    }

    public AlbumResponse getAlbumById(Long albumId) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));

        List<AlbumResponse.SongInfo> songs =
                album.getSongs() == null
                        ? List.<AlbumResponse.SongInfo>of()
                        : album.getSongs().stream()
                        .map(song -> AlbumResponse.SongInfo.builder()
                                .id(song.getId())
                                .title(song.getTitle())
                                .genre(song.getGenre())
                                .playCount(song.getPlayCount())
                                .build())
                        .toList();

        return AlbumResponse.builder()
                .id(album.getId())
                .name(album.getName())
                .releaseDate(album.getReleaseDate())
                .artistName(album.getArtist().getUsername())
                .songs(songs)
                .build();
    }

    public Page<AlbumResponse> getAllAlbums(
            String artistUsername,
            int page,
            int size
    ) {

        Page<Album> albumPage;

        if (artistUsername != null && !artistUsername.isBlank()) {
            albumPage = albumRepository.findByArtist_Username(
                    artistUsername,
                    PageRequest.of(page, size)
            );
        } else {
            albumPage = albumRepository.findAll(
                    PageRequest.of(page, size)
            );
        }

        return albumPage.map(album -> AlbumResponse.builder()
                .id(album.getId())
                .name(album.getName())
                .releaseDate(album.getReleaseDate())
                .artistName(album.getArtist().getUsername())
                .build());
    }

    @Transactional
    public void deleteAlbum(Long albumId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser artist = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));

        if (!album.getArtist().getId().equals(artist.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your album");
        }

        if (album.getSongs() != null && !album.getSongs().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot delete album with songs inside"
            );
        }

        albumRepository.delete(album);
    }

    @Transactional
    public void updateAlbum(Long albumId,
                            String name,
                            String description,
                            String releaseDateStr,
                            MultipartFile coverImage) throws IOException {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser artist = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));

        if (!album.getArtist().getId().equals(artist.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your album");
        }

        if (name != null) album.setName(name);
        if (description != null) album.setDescription(description);

        if (releaseDateStr != null && !releaseDateStr.isBlank()) {
            album.setReleaseDate(LocalDate.parse(releaseDateStr));
        }

        if (coverImage != null && !coverImage.isEmpty()) {

            String imagePath = fileStorageProperties.getImagePath();
            File uploadDir = new File(imagePath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();

            File destination = new File(uploadDir, fileName);
            coverImage.transferTo(destination);

            album.setCoverImagePath(fileName);
        }

        albumRepository.save(album);
    }
}