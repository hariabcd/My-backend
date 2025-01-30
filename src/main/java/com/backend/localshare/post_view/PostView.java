package com.backend.localshare.post_view;

import com.backend.localshare.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "post_views", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "post_id"})})
public class PostView {
    @Id
    @SequenceGenerator(
            name = "post_view-seq",
            sequenceName = "post_view-seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_view-seq"
    )
    private Long viewId;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id", referencedColumnName = "postId", nullable = false)
    private Post postId;
    private Long userId;
}
