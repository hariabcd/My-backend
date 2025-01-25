package com.backend.localshare.status_reaction;

import com.backend.localshare.status.Status;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
public class StatusReaction {
    @Id
    @SequenceGenerator(
            name = "status_reaction-seq",
            sequenceName = "status_reaction-seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "status_reaction-seq"
    )
    private Long reactionId;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "statusId", nullable = false)
    private Status status;
    @Timestamp
    private LocalDateTime reactedAt;
    private Long userId;
    @Enumerated(value = EnumType.STRING)
    private StatusReactionType reactionType;

}
