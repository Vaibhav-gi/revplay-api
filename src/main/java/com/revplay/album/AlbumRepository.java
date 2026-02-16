package com.revplay.album;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtist_Id(Long artistId);

    Page<Album> findByArtist_Username(String username, Pageable pageable);
}