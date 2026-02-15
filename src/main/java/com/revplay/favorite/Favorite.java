package com.revplay.favorite;

import com.revplay.song.Song;
import com.revplay.user.RpUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "song_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RpUser user;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;
}
