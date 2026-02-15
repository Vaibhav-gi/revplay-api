package com.revplay.analytics;

import com.revplay.analytics.dto.ArtistDashboardResponse;
import com.revplay.favorite.FavoriteRepository;
import com.revplay.song.Song;
import com.revplay.song.SongRepository;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistAnalyticsService {

    private final SongRepository songRepository;
    private final FavoriteRepository favoriteRepository;
    private final RpUserRepository userRepository;

    public ArtistDashboardResponse getDashboard() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        RpUser artist = userRepository.findByUsername(username)
                .orElseThrow();

        Long totalSongs = songRepository.countByArtistId(artist.getId());
        Long totalPlays = songRepository.totalPlaysByArtist(artist.getId());
        Long totalFavorites =
                favoriteRepository.countFavoritesForArtist(artist.getId());

        var topSongs = songRepository
                .findTop5ByArtistIdOrderByPlayCountDesc(artist.getId())
                .stream()
                .map(Song::getTitle)
                .toList();

        return ArtistDashboardResponse.builder()
                .totalSongs(totalSongs == null ? 0 : totalSongs)
                .totalPlays(totalPlays == null ? 0 : totalPlays)
                .totalFavorites(totalFavorites == null ? 0 : totalFavorites)
                .topSongs(topSongs)
                .build();
    }
}

