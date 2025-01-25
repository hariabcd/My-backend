package com.backend.localshare.post_stars;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/post-star")
@RequiredArgsConstructor
public class PostStarController {

    private final PostStarService postStarService;

    @PutMapping("/{postId}/stars/increment")
    public String incrementStars(@PathVariable Long postId) {
        postStarService.incrementPostLikes(postId);
        return "Stars incremented for post ID: " + postId;
    }

    @PutMapping("/{postId}/likes/decrement")
    public String decrementStars(@PathVariable Long postId) {
        postStarService.decrementPostLikes(postId);
        return "Stars decremented for post ID: " + postId;
    }
}
