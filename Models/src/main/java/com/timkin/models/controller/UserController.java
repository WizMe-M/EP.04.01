package com.timkin.models.controller;

import com.timkin.models.entity.User;
import com.timkin.models.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/search")
    public String searchUsers(
            @RequestParam(name = "s") String searchString,
            Model model
    ) {
        List<User> filtered = repository.findByLoginContainsIgnoreCase(searchString);
        model.addAttribute("users", filtered);
        return "users/all_users";
    }

    @GetMapping("/add")
    public String openAddUserPage(User user) {
        return "users/add_new_user";
    }

    @PostMapping("/add")
    public String createUser(
            @Valid User user,
            BindingResult validState) {
        if (validState.hasErrors()) {
            return "users/add_new_user";
        }
        repository.save(user);
        return "redirect:/users/all";
    }

    @GetMapping("/{profile_name}")
    public String openProfile(
            @PathVariable(name = "profile_name") String login,
            Model model,
            User profile
    ) {
        Optional<User> found = repository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }
        profile = found.get();
        model.addAttribute("profile", profile);
        return "users/profile";
    }

    @GetMapping("/{profile_name}/edit")
    public String openEditProfile(
            @PathVariable(name = "profile_name") String login,
            Model model,
            @ModelAttribute(name = "profile") User user
    ) {
        Optional<User> found = repository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }
        user = found.get();
        model.addAttribute("profile", user);
        return "users/edit_profile";
    }

    @PostMapping("/{profile_name}/edit")
    public String saveEditProfile(
            @PathVariable(name = "profile_name") String login,
            @ModelAttribute(name = "profile") @Valid User user,
            BindingResult validState
    ) {
        Optional<User> found = repository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }

        if (validState.hasErrors()) {
            return "users/edit_profile";
        }

        repository.save(user);
        return "redirect:/users/" + user.getLogin();
    }

    @GetMapping("/{profile_name}/delete")
    public String deleteUser(
            @PathVariable(name = "profile_name") String login
    ) {
        Optional<User> found = repository.findByLogin(login);
        if (found.isPresent()) {
            User deleting = found.get();
            repository.delete(deleting);
        }
        return "redirect:/users/all";
    }
}
