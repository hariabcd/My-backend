package com.backend.localshare.status;

import com.backend.localshare.post.ContentType;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Status {
    @Id
    @SequenceGenerator(
            name = "status-seq",
            sequenceName = "status-seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "status-seq"
    )
    private Long statusId;
    @Column(nullable = false, length = 500, unique = true)
    private String statusUrl;
    @Enumerated(value = EnumType.STRING)
    private ContentType statusType;
    @Timestamp
    private LocalDateTime createdAt;
    @Timestamp
    private LocalDateTime expiredAt;
    private Long userId;
    private Integer views;
    private Integer stars;
    @Column(nullable = false, length = 225, unique = true)
    private String objectKey;
    private String versionId;
    private String caption;
}