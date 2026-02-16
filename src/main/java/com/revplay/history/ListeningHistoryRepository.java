package com.revplay.history;

import java.util.List;
import com.revplay.user.RpUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListeningHistoryRepository
        extends JpaRepository<ListeningHistory, Long> {

    List<ListeningHistory> findTop50ByUser_IdOrderByPlayedAtDesc(Long userId);

    List<ListeningHistory> findByUser_IdOrderByPlayedAtDesc(Long userId);

    void deleteByUser_Id(Long userId);
}
