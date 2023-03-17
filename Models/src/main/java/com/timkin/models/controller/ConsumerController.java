package com.timkin.models.controller;

import com.timkin.models.entity.Consumer;
import com.timkin.models.repo.ConsumerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("consumers")
public class ConsumerController {
    private final ConsumerRepository repository;

    public ConsumerController(ConsumerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/suppliers/all";
    }

    @GetMapping("/all")
    public String openAll(Model model) {
        List<Consumer> all = repository.findAll();
        model.addAttribute("suppliers", all);
        return "suppliers/all";
    }

    @GetMapping("{id}")
    public String openSupplier(
            @PathVariable int id,
            Model model
    ) {
        Optional<Consumer> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/suppliers/all";
        }

        Consumer consumer = found.get();
        model.addAttribute("supplier", consumer);
        return "suppliers/details";
    }

    @GetMapping("/add")
    public String openAddSupplier(@ModelAttribute Consumer consumer) {
        return "suppliers/add";
    }

    @PostMapping("/add")
    public String addSupplier(
            @ModelAttribute @Valid Consumer consumer,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "suppliers/add";
        }

        repository.save(consumer);
        return "redirect:/suppliers/all";
    }

    @GetMapping("{id}/edit")
    public String openEditSupplier(
            @PathVariable int id,
            Model model
    ) {
        Optional<Consumer> supplier = repository.findById(id);
        if (supplier.isEmpty()) {
            return "redirect:/suppliers/all";
        }
        model.addAttribute("supplier", supplier.get());
        return "suppliers/edit";
    }

    @PostMapping("{id}/edit")
    public String editSupplier(
            @PathVariable String id,
            @ModelAttribute @Valid Consumer consumer,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "suppliers/edit";
        }
        repository.save(consumer);
        return "redirect:/suppliers/all";
    }

    @GetMapping("{id}/delete")
    public String deleteSupplier(@PathVariable int id) {
        repository.deleteById(id);
        return "redirect:/suppliers/all";
    }
}
