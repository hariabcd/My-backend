package com.backend.localshare.post_stars;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostStarService {
    private final PostStarRepository postStarRepository;

    @Transactional
    public void incrementPostLikes(Long postId) {
        PostStars postStars = postStarRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        postStars.setStarsCount(postStars.getStarsCount() + 1);
        postStarRepository.save(postStars);
    }

    @Transactional
    public void decrementPostLikes(Long postId) {
        PostStars postStars = postStarRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        if (postStars.getStarsCount() > 0) {
            postStars.setStarsCount(postStars.getStarsCount() - 1);
            postStarRepository.save(postStars);
        }
    }
}
