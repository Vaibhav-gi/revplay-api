package com.revplay.album.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AlbumResponse {

    private Long id;
    private String name;
    private LocalDate releaseDate;
    private String artistName;

    private List<SongInfo> songs;

    @Data
    @Builder
    public static class SongInfo {
        private Long id;
        private String title;
        private String genre;
        private Long playCount;
    }
}
