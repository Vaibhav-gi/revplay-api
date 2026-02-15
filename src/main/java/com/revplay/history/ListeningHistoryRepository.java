package com.revplay.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListeningHistoryRepository
        extends JpaRepository<ListeningHistory, Long> {

    @Query("""
        SELECT COUNT(h)
        FROM ListeningHistory h
        WHERE h.song.artist.id = :artistId
    """)
    Long countPlaysForArtist(@Param("artistId") Long artistId);
}
