package com.revplay.artist.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ArtistProfileResponse {

    private Long artistId;
    private String username;
    private String bio;
    private String genre;

    private List<SongInfo> songs;
    private List<AlbumInfo> albums;

    @Data
    @Builder
    public static class SongInfo {
        private Long id;
        private String title;
        private String genre;
        private Long playCount;
        private LocalDate releaseDate;
    }

    @Data
    @Builder
    public static class AlbumInfo {
        private Long id;
        private String name;
        private LocalDate releaseDate;
    }
}
