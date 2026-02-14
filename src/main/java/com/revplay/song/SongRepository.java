package com.revplay.song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {

    List<Song> findByArtistId(Long artistId);

    List<Song> findByTitleContainingIgnoreCase(String keyword);

    Page<Song> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
