package com.revplay.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rp_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RpUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String displayName;

    private String bio;

    private String genre;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;
}