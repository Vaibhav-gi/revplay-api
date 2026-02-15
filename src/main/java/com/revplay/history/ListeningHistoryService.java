package com.revplay.history;

import com.revplay.song.Song;
import com.revplay.song.SongRepository;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ListeningHistoryService {

    private final ListeningHistoryRepository historyRepository;
    private final RpUserRepository userRepository;
    private final SongRepository songRepository;

    @Transactional
    public void recordPlay(Long songId) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow();

        Song song = songRepository.findById(songId)
                .orElseThrow();

        ListeningHistory history = ListeningHistory.builder()
                .user(user)
                .song(song)
                .playedAt(LocalDateTime.now())
                .build();

        historyRepository.save(history);
    }

    public Object getRecentHistory() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow();

        return historyRepository
                .findByUserOrderByPlayedAtDesc(
                        user,
                        PageRequest.of(0, 50)
                );
    }

    @Transactional
    public void clearHistory() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow();

        historyRepository.deleteByUser(user);
    }
}