package com.revplay.song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {

    List<Song> findByArtistId(Long artistId);

    List<Song> findByTitleContainingIgnoreCase(String keyword);

    Page<Song> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Song s WHERE s.artist.id = :artistId")
    Long countByArtistId(Long artistId);

    @Query("SELECT SUM(s.playCount) FROM Song s WHERE s.artist.id = :artistId")
    Long totalPlaysByArtist(Long artistId);

    List<Song> findTop5ByArtistIdOrderByPlayCountDesc(Long artistId);


}
