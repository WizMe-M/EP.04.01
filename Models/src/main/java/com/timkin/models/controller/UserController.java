package com.timkin.models.controller;

import com.timkin.models.entity.User;
import com.timkin.models.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String openAllUsersTable(Model model) {
        List<User> all = repository.findAll();
        model.addAttribute("users", all);
        return "users/all_users";
    }

    @GetMapping("/add")
    public String openAddUserPage() {
        return "users/add_new_user";
    }

    @PostMapping("/add")
    public String createUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam int age
    ) {
        User user = new User(login, password, age);
        repository.save(user);
        return "redirect:/users/all";
    }

    @GetMapping("/profile")
    public String openProfile() {
        return "users/profile";
    }
}
