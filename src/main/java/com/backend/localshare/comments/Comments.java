package com.backend.localshare.comments;

import com.backend.localshare.post.Post;
import com.backend.localshare.user.User;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;

//@Entity
//public class Comments {
//    @Id
//    @SequenceGenerator(
//            name = "comments-seq",
//            sequenceName = "comments-seq",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "comments-seq"
//    )
//    private Integer commentId;
//    @Column(length = 512, nullable = false)
//    private String commentContent;
//    @ManyToOne
//    @JoinColumn(name = "post", referencedColumnName = "postId", nullable = false)
//    private Post postId;
//    @ManyToOne
//    @JoinColumn(name = "user", referencedColumnName = "userId", nullable = false)
//    private User userId;
//    @Timestamp
//    private LocalDateTime createdAt;
//}
