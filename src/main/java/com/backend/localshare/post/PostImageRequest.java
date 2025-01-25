package com.backend.localshare.post;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostImageRequest {
    private String caption;
    private boolean isCommentedOn;
}
