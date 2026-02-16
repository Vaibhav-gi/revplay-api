package com.revplay.album.dto;

import lombok.Data;

@Data
public class UpdateAlbumRequest {

    private String name;
    private String releaseDate;
}
