package com.revplay.history;

import com.revplay.song.Song;
import com.revplay.song.SongRepository;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListeningHistoryService {

    private final ListeningHistoryRepository historyRepository;
    private final RpUserRepository userRepository;
    private final SongRepository songRepository;

    public void recordPlay(Long songId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        ListeningHistory history = ListeningHistory.builder()
                .user(user)
                .song(song)
                .playedAt(LocalDateTime.now())
                .build();

        historyRepository.save(history);
    }

    public List<ListeningHistory> getRecentHistory() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return historyRepository
                .findTop50ByUser_IdOrderByPlayedAtDesc(user.getId());
    }

    public List<ListeningHistory> getFullHistory() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return historyRepository
                .findByUser_IdOrderByPlayedAtDesc(user.getId());
    }

    public void clearHistory() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        historyRepository.deleteByUser_Id(user.getId());
    }
}