package com.revplay.album;

import com.revplay.song.Song;
import com.revplay.user.RpUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "albums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String name;
    private LocalDate releaseDate;
    private String coverImagePath;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private RpUser artist;

    @OneToMany(mappedBy = "album")
    private List<Song> songs;
}
