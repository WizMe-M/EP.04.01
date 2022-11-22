package com.timkin.models.controller;

import com.timkin.models.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String openAllUsersTable(){
        return "users/all_users";
    }
}
