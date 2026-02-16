package com.revplay.song.dto;

import com.revplay.song.SongVisibility;
import lombok.Data;

@Data
public class UpdateSongRequest {

    private String title;
    private String genre;
    private Long albumId;
    private Integer duration;
    private SongVisibility visibility;
}
