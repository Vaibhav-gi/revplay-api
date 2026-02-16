package com.revplay.queue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaybackSettingsRepository
        extends JpaRepository<PlaybackSettings, Long> {

    Optional<PlaybackSettings> findByUser_Id(Long userId);
}
