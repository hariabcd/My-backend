package com.backend.localshare.post;

import com.backend.localshare.user.User;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Post {

    @Id
    @SequenceGenerator(
            name = "post-seq",
            sequenceName = "post-seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post-seq"
    )
    private Long postId;

    private String caption;

    @Enumerated(value = EnumType.STRING)
    private ContentType contentType;

    private Integer postStar;

    private boolean isCommentedOn;

    @Timestamp
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 500, unique = true)
    private String imageKey;

    private String versionId;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "\"user\"", referencedColumnName = "userId", nullable = false)
    private User postedBy;

    private Integer views;

    @Column(name = "views_count", nullable = false)
    private Integer viewsCount = 0;
}