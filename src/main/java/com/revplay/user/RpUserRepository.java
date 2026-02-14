package com.revplay.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RpUserRepository extends JpaRepository<RpUser, Long> {

    Optional<RpUser> findByEmail(String email);

    Optional<RpUser> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}