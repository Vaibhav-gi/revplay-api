package com.revplay.song.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SongResponse {

    private Long id;
    private String title;
    private String genre;
    private int duration;
    private String artistName;
    private LocalDate releaseDate;
    private long playCount;
}
