package com.revplay.history;

import com.revplay.user.RpUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListeningHistoryRepository
        extends JpaRepository<ListeningHistory, Long> {

    Page<ListeningHistory> findByUserOrderByPlayedAtDesc(
            RpUser user,
            Pageable pageable
    );

    void deleteByUser(RpUser user);
}
