package com.backend.localshare.post_view;

import com.backend.localshare.post.Post;
import com.backend.localshare.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostViewService {
    private final PostViewRepository postViewRepository;

    private final PostRepository postRepository;

    public Post createPost(Post post) {
        post.setViews(0); // Initialize views to 0
        return postRepository.save(post);
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public void incrementViewCount(Long postId, Long userId) {
        // Check if the user has already viewed the post
//        if (postViewRepository.findByUserIdAndPostId(userId, postId).isEmpty()) {
//            // Record the user's view
//            PostView postView = new PostView();
//            postView.setUserId(userId);
////            postView.setPostId(postId);
//            postViewRepository.save(postView);
//
//            // Increment the post's view count
//            Post post = postRepository.findById(postId)
//                    .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
//            post.setViewsCount(post.getViewsCount() + 1);
//            postRepository.save(post);
//        }
    }

}
