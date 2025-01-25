package com.backend.localshare.post_view;

import com.backend.localshare.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/postView")
@RequiredArgsConstructor
public class PostViewController {

    private final PostViewService postViewService;

    @PostMapping("/createPostView")
        @PutMapping("/{postId}/views/increment")
        public String incrementViews(@RequestParam Long userId,
                                     @PathVariable Long postId) {
            postViewService.incrementViewCount(userId, postId);
            return "View count incremented for post ID: " + postId;
        }

}
