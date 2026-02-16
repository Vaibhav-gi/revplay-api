package com.revplay.queue;

import com.revplay.song.Song;
import com.revplay.user.RpUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "playback_queue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaybackQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int position;

    private LocalDateTime addedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private RpUser user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;
}