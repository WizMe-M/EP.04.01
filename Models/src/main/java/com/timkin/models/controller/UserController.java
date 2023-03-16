package com.timkin.models.controller;

import com.timkin.models.entity.Role;
import com.timkin.models.entity.User;
import com.timkin.models.exceptions.UserNotFoundException;
import com.timkin.models.repo.ClientRepository;
import com.timkin.models.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAnyAuthority('Administrator')")
public class UserController {

    //region ctor
    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    public UserController(
            UserService service,
            PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }
    //endregion

    //region table
    @GetMapping
    public String index() {
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String openAllUsersTable(Model model) {
        List<User> all = service.findAll();
        model.addAttribute("users", all);
        return "users/all_users";
    }

    @GetMapping("/search")
    public String searchUsers(
            @RequestParam(name = "s") String searchString,
            Model model
    ) {
        List<User> filtered = service.findAll(searchString);
        model.addAttribute("users", filtered);
        return "users/all_users";
    }
    //endregion

    //region add user
    @GetMapping("/add")
    public String openAddUserPage(
            @ModelAttribute User user,
            Model model
    ) {
        Role[] roles = Role.values();
        model.addAttribute("roles", roles);
        return "users/add_new_user";
    }

    @PostMapping("/add")
    public String addUser(
            Model model,
            @Valid User user,
            BindingResult validationState,
            @RequestParam Role role
    ) {
        user.setRoles(Collections.singleton(role));
        if (validationState.hasErrors()) {
            Role[] roles = Role.values();
            model.addAttribute("roles", roles);
            return "users/add_new_user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.add(user);
        return "redirect:/users/all";
    }
    //endregion

    //region details
    @GetMapping("/{login}/details")
    @PreAuthorize("hasAnyAuthority('UnverifiedUser', 'Administrator', 'Technic', 'Seller')")
    public String openDetails(
            @PathVariable
            String login,
            @ModelAttribute("details") User details,
            Model model
    ) throws UserNotFoundException {
        details = service.find(login);
        model.addAttribute("details", details);

        return "users/details";
    }

    @GetMapping("/{login}/details/edit")
    public String openEditDetails(
            @PathVariable String login,
            Model model,
            @ModelAttribute("details") User details
    ) throws UserNotFoundException {
        details = service.find(login);
        model.addAttribute("details", details);

        Role[] roles = Role.values();
        model.addAttribute("available_roles", roles);
        return "users/edit_details";
    }

    @PostMapping("/{login}/details/edit")
    public String editDetails(
            @PathVariable String login,
            Model model,
            @ModelAttribute("details") User details,
            BindingResult validationState,
            @RequestParam Role role
    ) throws UserNotFoundException {
        service.find(login);
        details.setRoles(Collections.singleton(role));
        if (validationState.hasErrors()) {
            Role[] roles = Role.values();
            model.addAttribute("available_roles", roles);
            return "users/edit_details";
        }
        service.edit(details);
        return "redirect:/users/all";
    }
    //endregion

    //region delete user
    @GetMapping("/{login}/delete")
    public String deleteUser(
            @PathVariable String login
    ) throws UserNotFoundException {
        service.delete(login);
        return "redirect:/users/all";
    }
    //endregion
}