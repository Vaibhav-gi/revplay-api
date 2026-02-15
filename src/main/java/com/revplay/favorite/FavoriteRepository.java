package com.revplay.favorite;

import com.revplay.song.Song;
import com.revplay.user.RpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndSong(RpUser user, Song song);

    void deleteByUserAndSong(RpUser user, Song song);

    @Query("""
        SELECT COUNT(f)
        FROM Favorite f
        WHERE f.song.artist.id = :artistId
    """)
    Long countFavoritesForArtist(@Param("artistId") Long artistId);

}