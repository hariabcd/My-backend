package com.backend.localshare.status;

import com.backend.localshare.dto.StatusDTO;
import com.backend.localshare.post.ContentType;
import com.backend.localshare.post.Post;
import com.backend.localshare.status_reaction.StatusReactionType;
import com.backend.localshare.user.User;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jdk.jfr.Timestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    boolean existsByImageKey(String key);

    // Fetch all statuses created after a specific time
    List<Status> findByCreatedAtAfter(LocalDateTime cutoffTime);

    // Delete all statuses older than a specific time
    @Modifying
    @Query("DELETE FROM Status s WHERE s.createdAt < :cutoffTime")
    Integer deleteByCreatedAtBefore(LocalDateTime cutoffTime);

    // Delete statuses where expiredAt is less than or equal to the current time
    @Modifying
    @Transactional
    @Query("DELETE FROM Status s WHERE s.expiredAt <= :now")
    Integer deleteExpiredStatuses(@Param("now") LocalDateTime now);


    @Query("SELECT new com.backend.localshare.dto.StatusDTO( " +
            "s.statusId, s.statusUrl s.statusType, s.createdAt, " +
            "s.expiredAt, s.userId, s.views, s.stars, s.caption " +
            ") FROM Status s " +
            "WHERE s.userId IN (SELECT u.userId FROM User u WHERE u.location = :location) " +
            "AND s.statusId NOT IN " +
            "(SELECT v.status.statusId FROM StatusReaction v WHERE v.viewer = :userId " +
            "AND v.status.statusId = s.statusId AND v.reactionType = 'STATUS_VIEW') " +
            "AND s.expiredAt > :currentTime" +
            "ORDER BY s.createdAt DESC")
    Page<StatusDTO> getUnseenStatus(
            @Param("location") String location, @Param("currentUserId") Long currentUserId,
            Pageable pageable, @Param("currentTime") LocalDateTime currentTime
    );

    @Query("SELECT new com.backend.localshare.dto.StatusDTO( " +
            "s.statusId, s.statusUrl s.statusType, s.createdAt, " +
            "s.expiredAt, s.userId, s.views, s.stars, s.caption " +
            ") FROM Status s " +
            "WHERE s.userId IN (SELECT u.userId FROM User u WHERE u.location = :location) " +
            "AND s.statusId IN " +
            "(SELECT v.status.statusId FROM StatusReaction v WHERE v.viewer = :userId " +
            "AND v.status.statusId = s.statusId AND v.reactionType = 'STATUS_VIEW') " +
            "AND s.expiredAt > :currentTime" +
            "ORDER BY s.createdAt DESC")
    Page<StatusDTO> getSeenStatus(
            @Param("location") String location, @Param("currentUserId") Long currentUserId,
            Pageable pageable, @Param("currentTime") LocalDateTime currentTime
    );

}
