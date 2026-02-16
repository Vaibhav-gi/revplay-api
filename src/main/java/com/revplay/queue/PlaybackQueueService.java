package com.revplay.queue;

import com.revplay.song.Song;
import com.revplay.song.SongRepository;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaybackQueueService {

    private final PlaybackQueueRepository queueRepository;
    private final SongRepository songRepository;
    private final RpUserRepository userRepository;

    private RpUser getCurrentUser() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void addToQueue(Long songId) {

        RpUser user = getCurrentUser();

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        int nextPosition =
                queueRepository.findByUser_IdOrderByPositionAsc(user.getId())
                        .size() + 1;

        PlaybackQueue queue = PlaybackQueue.builder()
                .song(song)
                .user(user)
                .position(nextPosition)
                .addedAt(LocalDateTime.now())
                .build();

        queueRepository.save(queue);
    }

    public List<PlaybackQueue> getQueue() {
        RpUser user = getCurrentUser();
        return queueRepository
                .findByUser_IdOrderByPositionAsc(user.getId());
    }

    @Transactional
    public void clearQueue() {
        RpUser user = getCurrentUser();
        queueRepository.deleteByUser_Id(user.getId());
    }
}
