package com.backend.localshare.dto;

import com.backend.localshare.post.ContentType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StatusDTO {

    private Long statusId;
    private String statusUrl;
    private ContentType statusType;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Long userId;
    private Integer views;
    private Integer stars;
    private String caption;
    private boolean isUserSeen;
    private String location;

    public StatusDTO() {}

    public StatusDTO(
            Long statusId,
            String statusUrl,
            ContentType statusType,
            LocalDateTime createdAt,
            LocalDateTime expiredAt,
            Long userId,
            Integer views,
            Integer stars,
            String caption
    ) {
        this.statusId = statusId;
        this.statusUrl = statusUrl;
        this.statusType = statusType;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.userId = userId;
        this.views = views;
        this.stars = stars;
        this.caption = caption;
    }
}
