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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Objects;

@Controller
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService service;

    public AuthenticationController(UserService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/sign")
    public String openSignInAndUp(
            @RequestParam(name = "error", required = false) Object error,
            @ModelAttribute @Valid User user,
            BindingResult validationState,
            RedirectAttributes attributes
    ) {
        return "auth/signing";
    }

    @GetMapping("/login")
    public String openLogin(@RequestParam(name = "error", required = false) String error) {
        return Objects.equals(error, "") ? "redirect:/sign?error" : "redirect:/sign";
    }

    @GetMapping("/register")
    public String openRegistration() {
        return "redirect:/sign#sign-up-form";
    }

    @PostMapping("/register")
    public String  register(
            @ModelAttribute @Valid User user,
            BindingResult validationState,
            Model model,
            HttpServletRequest request,
            RedirectAttributes attributes
    ) throws UserNotFoundException {
        if (validationState.hasErrors()) {
            attributes.addFlashAttribute("user", user);
            attributes.addFlashAttribute("org.springframework.validation.BindingResult", validationState);
            return "redirect:/sign#sign-up-form";
        }

        if (service.existWithLogin(user.getLogin())) {
            model.addAttribute("message", "User with such login already exists!");
            return "redirect:/sign#sign-up-form";
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