package com.backend.localshare.status_reaction;

import com.backend.localshare.status.Status;
import com.backend.localshare.status.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatusReactionService {

    private final StatusReactionRepository statusReactionRepository;
    private final StatusRepository statusRepository;

    /**
     * Add a like to a status.
     */
    @Transactional
    public ResponseEntity<?> addStar(Long statusId, Long userId) {
        // Check if the status exists
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));

        // Check if the user has already liked star the status
        boolean isStatusExists = statusReactionRepository.exitsStarByUserIdAndStatusId(statusId, userId);
        if (isStatusExists) {
            throw new IllegalArgumentException("You already reacted to this status");
        }

        StatusReaction reaction = StatusReaction.builder()
                .reactionType(StatusReactionType.STATUS_STAR)
                .status(status)
                .reactedAt(LocalDateTime.now())
                .userId(userId)
                .build();
        Long reactionId = statusReactionRepository.save(reaction).getReactionId();
        return ResponseEntity.ok(reactionId);
    }

    /**
     * Remove a like from a status.
     */
    @Transactional
    public String removeStar(Long statusId, Long reactionId, Long userId) {
        // Check if the user has liked star the status
        boolean isStatusExists = statusReactionRepository.exitsStarByUserIdAndStatusId(statusId, userId);
        if (!isStatusExists) {
            throw new IllegalArgumentException("You are not reacted this status");
        }

        // Delete the like star reaction
        statusReactionRepository.deleteById(reactionId);

        return "Like removed successfully.";
    }

    /**
     * Record a view for a status.
     */
    @Transactional
    public ResponseEntity<?> addStatusView(Long statusId, Long userId) {
        // Check if the viewer has already viewed the status
        boolean isViewExists = statusReactionRepository.existsByStatusIdAndUserId(statusId, userId);
        if (isViewExists) {
            return ResponseEntity.ok().build();
        }
        Status status = statusRepository.findById(statusId).orElse(null);
        if(status == null) return ResponseEntity.ok().build();

        StatusReaction reaction = StatusReaction.builder()
                .reactionType(StatusReactionType.STATUS_VIEW)
                .status(status)
                .reactedAt(LocalDateTime.now())
                .userId(userId)
                .build();
        Long viewId = statusReactionRepository.save(reaction).getReactionId();
        return ResponseEntity.ok(viewId);
    }

    /**
     * Get the total number of views for a status.
     */
    @Transactional(readOnly = true)
    public Long getTotalReactionByStatus(
            Long statusId
    ) {
        return statusReactionRepository.getReactionsByStatusId(statusId);
    }
}
