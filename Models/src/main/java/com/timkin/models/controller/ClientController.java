package com.timkin.models.controller;

import com.timkin.models.entity.Client;
import com.timkin.models.repo.ClientRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
@PreAuthorize("hasAnyAuthority('Seller')")
public class ClientController {

    private final ClientRepository repository;

    public ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/clients/all";
    }

    @GetMapping("/all")
    public String openAll(Model model) {
        List<Client> all = repository.findAll();
        model.addAttribute("clients", all);
        return "clients/all_clients";
    }

    @GetMapping("{id}")
    public String openClient(
            @PathVariable int id,
            Model model
    ) {
        Optional<Client> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/clients/all";
        }

        Client client = found.get();
        model.addAttribute("client", client);
        return "clients/client";
    }

    @GetMapping("/add")
    public String openAddClient(@ModelAttribute Client client) {
        return "clients/add_client";
    }

    @PostMapping("/add")
    public String addClient(
            @ModelAttribute @Valid Client client,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "clients/add_client";
        }

        repository.save(client);
        return "redirect:/clients/all";
    }

    @GetMapping("{id}/edit")
    public String openEditClient(
            @PathVariable int id,
            Model model
    ) {
        Optional<Client> client = repository.findById(id);
        if (client.isEmpty()) {
            return "redirect:/clients/all";
        }
        model.addAttribute("client", client.get());
        return "clients/edit_client";
    }

    @PostMapping("{id}/edit")
    public String editClient(
            @PathVariable String id,
            @ModelAttribute @Valid Client client,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "clients/edit_client";
        }
        repository.save(client);
        return "redirect:/clients/all";
    }

    @GetMapping("{id}/delete")
    public String deleteClient(@PathVariable int id) {
        repository.deleteById(id);
        return "redirect:/clients/all";
    }
}
