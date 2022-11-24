package com.timkin.models.controller;

import com.timkin.models.entity.User;
import com.timkin.models.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String openAddUserPage(User user) {
        return "users/add_new_user";
    }

    @PostMapping("/add")
    public String createUser(
            User user) {
        repository.save(user);
        return "redirect:/users/all";
    }

    @GetMapping("/profile/{profile-name}")
    public String openProfile(
            @PathVariable(name = "profile-name") String login,
            Model model
    ) {
        User user = repository.findByLogin(login).orElseThrow();
        model.addAttribute("profile", user);
        return "users/profile";
    }
}
