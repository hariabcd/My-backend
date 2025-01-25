package com.backend.localshare.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/Users")
public class UserController {
    @GetMapping("/createUsers")
    public User createUser() {

    }

    @PutMapping("/updateUser")
    public User upDateUser() {

    }

    @GetMapping
    public User getUser(){

    }

    @DeleteMapping
    public User deleteUser() {

    }
}
