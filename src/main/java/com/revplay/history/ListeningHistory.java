package com.revplay.history;

import com.revplay.song.Song;
import com.revplay.user.RpUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "listening_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListeningHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RpUser user;

    @ManyToOne
    private Song song;

    private LocalDateTime playedAt;
}