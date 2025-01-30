package com.backend.localshare.post;

// Todo: use userId as path name for now  then we migrate to spring security to get the user details
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(
            value = "/upload-image/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadImage(
            @ModelAttribute PostImageRequest postRequest,
            @RequestParam("file") MultipartFile file,
            @PathVariable("userId") Long userId
    ) throws IOException {
        return postService.uploadImage(postRequest, userId, file);
    }

    @GetMapping("/get-all")
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Long id){
        postService.deletePost(id);
    }
}
