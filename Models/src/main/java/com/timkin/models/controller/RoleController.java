package com.timkin.models.controller;

import com.timkin.models.entity.Role;
import com.timkin.models.repo.RoleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository repository;

    public RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/roles/all";
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

    @GetMapping("/add")
    public String openAddRole(@ModelAttribute Role role) {
        return "roles/add_role";
    }

    @PostMapping("/add")
    public String createRole(
            @ModelAttribute @Valid Role role,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "roles/add_role";
        }
        repository.save(role);
        return "redirect:/roles";
    }

    @GetMapping("/{role_id}/edit")
    public String openRenameRole(
            @PathVariable(name = "role_id") int id,
            @ModelAttribute Role role,
            Model model
    ) {
        Optional<Role> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/roles/all";
        }

        role = found.orElseThrow();
        model.addAttribute(role);
        return "roles/edit_role";
    }

    @PostMapping("/{role_id}/edit")
    public String renameRole(
            @PathVariable(name = "role_id") int id,
            @ModelAttribute @Valid Role role,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "roles/edit_role";
        }

        repository.save(role);
        return "redirect:/roles/all";
    }

    @GetMapping("/{role_id}/delete")
    public String deleteRole(
            @PathVariable(name = "role_id") int id
    ) {
        repository.deleteById(id);
        return "redirect:/roles/all";
    }
}
