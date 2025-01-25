package com.backend.localshare.status_reaction;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusReactionRepository extends JpaRepository<StatusReaction, Long> {


    // Check if a user has already liked stars the status
    @Query("SELECT COUNT(r) > 0 THEN TRUE ELSE FALSE END" +
            "FROM StatusReaction r " +
            "WHERE r.status.statusId = :statusId AND r.userId = :userId " +
            "AND r.reactionType = 'STATUS_STAR' ")
    boolean exitsStarByUserIdAndStatusId(
            @Param("statusId") Long statusId, @Param("viewer") Long userId
    );

    // Delete a like stars reaction
    @Modifying
    @Transactional
    @Query("DELETE FROM StatusReaction r WHERE r.status.statusId = :statusId AND r.viewer = :viewer AND r.reactionType = 'LIKE'")
    void deleteStarsByStatusAndViewer(@Param("statusId") Long statusId,
                                      @Param("viewer") Long viewer);

    // Check if a user has already viewed the status
    @Query("SELECT COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM StatusReaction r " +
            "WHERE r.status.statusId = :statusId AND " +
            "r.userId = :userId AND r.reactionType = 'STATUS_VIEW'")
    boolean existsByStatusIdAndUserId(
            @Param("statusId") Long statusId, @Param("userId") Long userId
    );

    // Count total views for a status
    @Query("SELECT COUNT(r) FROM StatusReaction r " +
            "WHERE r.status.statusId = :statusId")
    Long getReactionsByStatusId(
            @Param("statusId") Long statusId
    );

}
