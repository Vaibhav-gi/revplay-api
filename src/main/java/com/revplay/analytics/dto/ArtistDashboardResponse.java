package com.revplay.analytics.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ArtistDashboardResponse {

    private Long totalSongs;
    private Long totalPlays;
    private Long totalFavorites;
    private List<String> topSongs;
}