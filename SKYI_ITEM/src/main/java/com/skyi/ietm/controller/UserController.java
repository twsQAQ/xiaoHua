package com.skyi.ietm.controller;

import com.skyi.ietm.model.User;
import com.skyi.ietm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public User login(String username){
        User user = userService.findUser(username);
        System.out.println(user);
        return user;
    }
}
