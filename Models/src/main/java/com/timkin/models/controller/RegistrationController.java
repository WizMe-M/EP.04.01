package com.timkin.models.controller;

import com.timkin.models.entity.Role;
import com.timkin.models.entity.User;
import com.timkin.models.exceptions.UserNotFoundException;
import com.timkin.models.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class RegistrationController {

    private final AuthenticationManager authenticationManager;
    private final UserService service;

    public RegistrationController(UserService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/register")
    public String openRegistration(@ModelAttribute User user) {
        return "auth/registration";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute User user,
            Model model,
            HttpServletRequest request
    ) throws UserNotFoundException {
        if (service.existWithLogin(user.getLogin())) {
            model.addAttribute("message", "User with such login already exists!");
            return "auth/registration";
        }
        user.setRoles(Collections.singleton(Role.User));
        service.add(user);

        User authorized = service.find(user.getLogin());
        authenticateUserAndSetSession(authorized, request);

        return "redirect:/home";
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String username = user.getLogin();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

}
