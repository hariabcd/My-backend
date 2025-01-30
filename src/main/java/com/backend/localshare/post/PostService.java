package com.backend.localshare.post;

import com.backend.localshare.exception.DatabaseOperationException;
import com.backend.localshare.s3.S3Service;
import com.backend.localshare.user.User;
import com.backend.localshare.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final S3Service s3;
//    @Value("${aws.s3.bucket}")
    private String bucket = "asdfg";

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public ResponseEntity<?> uploadImage(
            PostImageRequest postRequest, Long userId, MultipartFile file
    ) throws IOException {
        User user = userService.getUserById(userId);
        imageService.processImage(file);
        String type = imageService.detectMimeType(file.getInputStream());
        String key = generateObjectKey(type);
        try {
            String version = s3.putObject(
                    bucket, key, file, "max-age=86400, public", type
            ).versionId();
            String url = s3.getPublicUrl(bucket, key);
            Post post = Post.builder()
                    .caption(postRequest.getCaption())
                    .contentType(ContentType.IMAGE)
                    .postStar(0)
                    .imageUrl(url)
                    .isCommentedOn(postRequest.isCommentedOn())
                    .createdAt(LocalDateTime.now())
                    .imageKey(key)
                    .versionId(version)
                    .postedBy(user)
                    .views(1)
                    .build();

            var savedPost = postRepository.save(post).getPostId();
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            throw new DatabaseOperationException(e.getMessage(),
                    "There is an error occurred in uploadPost in PostService by save"
            );
        }
    }

    private String generateObjectKey(String type) {
        var characters = "1234567890abcdefghijklmnopqrstuvwxyz_";
        SecureRandom secureRandom = new SecureRandom();
        var extension = imageService.determineExtension(type);
        String key;
        do {
            StringBuilder codeBuilder = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                int randomIndex = secureRandom.nextInt(characters.length());
                codeBuilder.append(characters.charAt(randomIndex));
            }
            key = codeBuilder + extension;
        } while (postRepository.existsByImageKey(key));
        return key;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
