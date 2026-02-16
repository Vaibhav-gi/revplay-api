package com.revplay.song;

import com.revplay.album.Album;
import com.revplay.user.RpUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;

    private String genre;

    @Column(nullable = false)
    private int duration;

    @Column(name = "audio_path")
    private String audioPath;
    @Column(name = "cover_Image")
    private String coverImage;
    @Column(name = "play_count", nullable = false)
    private long playCount = 0;
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SongVisibility visibility = SongVisibility.PUBLIC;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private RpUser artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;


}
