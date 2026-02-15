package com.revplay.playlist;

import com.revplay.user.RpUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RpUser owner;

    @OneToMany(mappedBy = "playlist",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PlaylistSong> songs;
}