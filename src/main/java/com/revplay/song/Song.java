package com.revplay.song;

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

    private String title;

    private String genre;

    private int duration;

    private String audioPath;

    private String coverImage;

    private boolean isPublic = true;

    private long playCount = 0;

    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private RpUser artist;
}
