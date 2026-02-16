package com.revplay.queue;

import com.revplay.user.RpUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "playback_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaybackSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean shuffleEnabled = false;

    @Enumerated(EnumType.STRING)
    private RepeatMode repeatMode = RepeatMode.OFF;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private RpUser user;
}
