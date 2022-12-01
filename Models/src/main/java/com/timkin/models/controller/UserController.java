package com.timkin.models.controller;

import com.timkin.models.entity.Profile;
import com.timkin.models.entity.Role;
import com.timkin.models.entity.User;
import com.timkin.models.repo.ProfileRepository;
import com.timkin.models.repo.RoleRepository;
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

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserController(
            UserRepository userRepository,
            RoleRepository roleRepository,
            ProfileRepository profileRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public String index() {
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String openAllUsersTable(Model model) {
        List<User> all = userRepository.findAll();
        model.addAttribute("users", all);
        return "users/all_users";
    }

    @GetMapping("/search")
    public String searchUsers(
            @RequestParam(name = "s") String searchString,
            Model model
    ) {
        List<User> filtered = userRepository.findByLoginContainsIgnoreCase(searchString);
        model.addAttribute("users", filtered);
        return "users/all_users";
    }

    @GetMapping("/add")
    public String openAddUserPage(
            @ModelAttribute User user,
            Model model
    ) {
        List<Role> all = roleRepository.findAll();
        model.addAttribute("roles", all);
        return "users/add_new_user";
    }

    @PostMapping("/add")
    public String createUser(
            Model model,
            @Valid User user,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            List<Role> roles = roleRepository.findAll();
            model.addAttribute("roles", roles);
            return "users/add_new_user";
        }
        userRepository.save(user);
        return "redirect:/users/all";
    }

    @GetMapping("/{login}/details")
    public String openDetails(
            @PathVariable
            String login,
            @ModelAttribute("details") User details,
            Model model
    ) {
        Optional<User> found = userRepository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }
        details = found.get();
        model.addAttribute("details", details);

        return "users/details";
    }

    @GetMapping("/{login}/edit-details")
    public String openEditDetails(
            @PathVariable String login,
            Model model,
            @ModelAttribute("details") User details
    ) {
        Optional<User> found = userRepository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }
        details = found.get();
        model.addAttribute("details", details);

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "users/edit_details";
    }

    @PostMapping("/{login}/edit-details")
    public String saveChangedDetails(
            @PathVariable String login,
            Model model,
            @ModelAttribute("details") User details,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            List<Role> roles = roleRepository.findAll();
            model.addAttribute("roles", roles);
            return "users/edit_details";
        }
        userRepository.save(details);
        return "redirect:/users/all";
    }

    @GetMapping("/{login}/profile/create")
    public String createProfile(
            @PathVariable String login
    ) {
        Optional<User> found = userRepository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }
        User user = found.get();
        Profile profile = new Profile(user);
        profileRepository.save(profile);

        return "redirect:/users/%s/details".formatted(login);
    }

    @GetMapping("/{login}")
    public String openProfile(
            @PathVariable String login,
            Model model,
            User profile
    ) {
        Optional<User> found = userRepository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }
        profile = found.get();
        model.addAttribute("profile", profile);
        return "users/profile";
    }

    @GetMapping("/{login}/edit")
    public String openEditProfile(
            @PathVariable String login,
            Model model,
            @ModelAttribute(name = "profile") User user
    ) {
        Optional<User> found = userRepository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }
        user = found.get();
        model.addAttribute("profile", user);
        return "users/edit_profile";
    }

    @PostMapping("/{login}/edit")
    public String saveEditProfile(
            @PathVariable String login,
            @ModelAttribute(name = "profile") @Valid User user,
            BindingResult validationState
    ) {
        Optional<User> found = userRepository.findByLogin(login);
        if (found.isEmpty()) {
            return "redirect:/users/all";
        }

        if (validationState.hasErrors()) {
            return "users/edit_profile";
        }

        userRepository.save(user);
        return "redirect:/users/" + user.getLogin();
    }

    @GetMapping("/{login}/delete")
    public String deleteUser(
            @PathVariable String login
    ) {
        Optional<User> found = userRepository.findByLogin(login);
        if (found.isPresent()) {
            User deleting = found.get();
            userRepository.delete(deleting);
        }
        return "redirect:/users/all";
    }
}
