package com.timkin.models.controller;

import com.timkin.models.entity.Role;
import com.timkin.models.entity.User;
import com.timkin.models.repo.RoleRepository;
import com.timkin.models.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final RoleRepository roleRepository;
    private final UserService service;

    public AuthenticationController(RoleRepository roleRepository, UserService service) {
        this.roleRepository = roleRepository;
        this.service = service;
    }

    @GetMapping("/register")
    public String openRegistration(@ModelAttribute User user) {
        return "auth/registration";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute User user,
            Model model
    ) {
        if (service.existWithLogin(user.getLogin())) {
            model.addAttribute("message", "User with such login already exists!");
            return "auth/registration";
        }
        user.setRole(Collections.singleton(Role.User));
        service.add(user);
        return "redirect:/home";
    }
}
