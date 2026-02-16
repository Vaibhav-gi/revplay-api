package com.revplay.artist;

import com.revplay.album.AlbumRepository;
import com.revplay.artist.dto.ArtistProfileResponse;
import com.revplay.song.SongRepository;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final RpUserRepository userRepository;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    public ArtistProfileResponse getArtistProfile(Long artistId) {

        RpUser artist = userRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        if (!artist.getRole().name().equals("ARTIST")) {
            throw new RuntimeException("User is not an artist");
        }

        var songs = songRepository.findByArtist_Id(artistId)
                .stream()
                .map(song -> ArtistProfileResponse.SongInfo.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .genre(song.getGenre())
                        .playCount(song.getPlayCount())
                        .releaseDate(song.getReleaseDate())
                        .build())
                .toList();

        var albums = albumRepository.findByArtist_Id(artistId)
                .stream()
                .map(album -> ArtistProfileResponse.AlbumInfo.builder()
                        .id(album.getId())
                        .name(album.getName())
                        .releaseDate(album.getReleaseDate())
                        .build())
                .toList();

        return ArtistProfileResponse.builder()
                .artistId(artist.getId())
                .username(artist.getUsername())
                .bio(artist.getBio())
                .genre(artist.getGenre())
                .songs(songs)
                .albums(albums)
                .build();
    }
}