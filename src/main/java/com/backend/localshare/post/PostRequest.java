package com.backend.localshare.post;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostRequest {
    private String caption;
    private ContentType contentType;
    private boolean isCommentedOn;
    private Integer versionId;
    // Came from security authentication context holder
    private Integer userId;
    // > 500 mb fail
    private byte[] content;
    // websocket -> backend -> s3
    // s3 (secondary) -> lambda -> backend -> s3 (primary)
}

// 500 mb -> 5mb -> 1000 requests 1 request

// Optional network
// 500 mb -> 5 mb -> 1chuck -> [logic] -> s3 multipart -> s3 key, raw key
// (redis s3 key, raw key, userId) response/raw key

//  frontend -> chuck -> direct s3 secondary bucket store
// event generate -> lambda function trigger -> backend server call x
// x -> logic / compression -> s3 primary store   // complex

// in-memory ram 2 gb 500 mb
// redis ??? 500 mb
// postgres heavy