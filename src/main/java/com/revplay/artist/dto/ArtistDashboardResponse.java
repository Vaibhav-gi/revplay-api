package com.revplay.artist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArtistDashboardResponse {

    private long totalSongs;
    private long totalPlays;
    private long totalFavorites;
}
