package com.backend.localshare.comments;

import com.backend.localshare.user.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/comment")
public class CommentController {
    @PostMapping("/createComment")
    public User createComment() {

    }

    @GetMapping("/getByPost/{id}")
    public User getComment() {

    }

    @PutMapping("/upDateComment")
    public User UpdateComment(){

    }
    @DeleteMapping("/deleteComment")
    public String deleteComment(){

    }
}
