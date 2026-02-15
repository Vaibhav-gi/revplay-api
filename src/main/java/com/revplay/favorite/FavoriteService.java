package com.revplay.favorite;

import com.revplay.song.Song;
import com.revplay.song.SongRepository;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final SongRepository songRepository;
    private final RpUserRepository userRepository;

    public void addFavorite(Long songId) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        if (favoriteRepository.existsByUserAndSong(user, song)) {
            throw new RuntimeException("Already favorited");
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .song(song)
                .build();

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(Long songId) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        favoriteRepository.deleteByUserAndSong(user, song);
    }
}