package com.backend.localshare.post_view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostViewRepository extends JpaRepository<PostView, Long> {
//    Optional<PostView> findByUserIdAndPostId(Long userId, Long postId);
}
