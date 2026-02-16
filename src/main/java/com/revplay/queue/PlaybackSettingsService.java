package com.revplay.queue;

import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaybackSettingsService {

    private final PlaybackSettingsRepository repository;
    private final RpUserRepository userRepository;

    private RpUser getCurrentUser() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private PlaybackSettings getOrCreate(RpUser user) {
        return repository.findByUser_Id(user.getId())
                .orElseGet(() -> repository.save(
                        PlaybackSettings.builder()
                                .user(user)
                                .build()
                ));
    }

    public PlaybackSettings getSettings() {
        RpUser user = getCurrentUser();
        return getOrCreate(user);
    }

    public void toggleShuffle() {
        RpUser user = getCurrentUser();
        PlaybackSettings settings = getOrCreate(user);

        settings.setShuffleEnabled(!settings.isShuffleEnabled());
        repository.save(settings);
    }

    public void setRepeatMode(RepeatMode mode) {
        RpUser user = getCurrentUser();
        PlaybackSettings settings = getOrCreate(user);

        settings.setRepeatMode(mode);
        repository.save(settings);
    }
}
