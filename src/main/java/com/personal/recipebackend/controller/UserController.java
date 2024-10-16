package com.personal.recipebackend.controller;

import com.personal.recipebackend.model.Recipe;
import com.personal.recipebackend.model.User;
import com.personal.recipebackend.repository.UserRepository;
import com.personal.recipebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserService service;

    @PostMapping("/register")
    public User saveUser(@RequestBody User user){
        return service.registerService(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.loginService(user);
    }
}
