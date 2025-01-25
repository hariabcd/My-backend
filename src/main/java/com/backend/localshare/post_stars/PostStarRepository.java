package com.backend.localshare.post_stars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostStarRepository extends JpaRepository<PostStars, Long> {
}
