package com.backend.localshare.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/Users")
public class UserController {
    @GetMapping("/createUsers")
    public User createUser() {
        return null;
    }

    @PutMapping("/updateUser")
    public User upDateUser() {
        return null;
    }

    @GetMapping
    public User getUser(){
        return null;
    }

    @DeleteMapping
    public User deleteUser() {
        return null;
    }
}
