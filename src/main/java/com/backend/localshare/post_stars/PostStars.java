package com.backend.localshare.post_stars;

import com.backend.localshare.post.Post;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
public class PostStars {
    @Id
    @SequenceGenerator(
            name = "post_stars-seq",
            sequenceName = "post_stars-seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_stars-seq"
    )
    private int starId;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id", referencedColumnName = "postId", nullable = false)
    private Post postId;
    @Timestamp
    private LocalDateTime likedAt;
    private Long userId;

    @Column(name = "views_count", nullable = false)
    private Integer viewsCount = 0;

    @Column(name = "stars_count", nullable = false)
    private Integer StarsCount = 0;
}
