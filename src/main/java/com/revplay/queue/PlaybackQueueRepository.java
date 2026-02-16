package com.revplay.queue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaybackQueueRepository
        extends JpaRepository<PlaybackQueue, Long> {

    List<PlaybackQueue> findByUser_IdOrderByPositionAsc(Long userId);

    void deleteByUser_Id(Long userId);
}