package com.revplay.song;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByArtistId(Long artistId);

    List<Song> findByTitleContainingIgnoreCase(String keyword);
}
