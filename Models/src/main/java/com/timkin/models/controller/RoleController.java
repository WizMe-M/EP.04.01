package com.timkin.models.controller;

import com.timkin.models.entity.Role;
import com.timkin.models.repo.RoleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository repository;

    public RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/all";
    }

    @GetMapping("/all")
    public String listAllRoles(Model model) {
        List<Role> all = repository.findAll();
        model.addAttribute("roles", all);
        return "roles/all_roles";
    }

    @GetMapping("/search")
    public String searchRoles(
            @RequestParam(name = "s") String searchString,
            Model model
    ) {
        List<Role> filtered = repository.findByNameContainsIgnoreCase(searchString);
        model.addAttribute("roles", filtered);
        return "roles/all_roles";
    }
}
