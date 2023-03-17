package com.timkin.models.controller;

import com.timkin.models.entity.Country;
import com.timkin.models.repo.CountryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("countries")
public class CountryController {
    private final CountryRepository repository;

    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/countries/all";
    }

    @GetMapping("/all")
    public String openAll(Model model) {
        List<Country> all = repository.findAll();
        model.addAttribute("countries", all);
        return "countries/all";
    }

    @GetMapping("/add")
    public String openAdd(@ModelAttribute Country country) {
        return "countries/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute @Valid Country country,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "countries/add";
        }

        repository.save(country);
        return "redirect:/countries/all";
    }

    @GetMapping("{id}/edit")
    public String openEdit(
            @PathVariable int id,
            Model model
    ) {
        Optional<Country> country = repository.findById(id);
        if (country.isEmpty()) {
            return "redirect:/countries/all";
        }
        model.addAttribute("country", country.get());
        return "countries/edit";
    }

    @PostMapping("{id}/edit")
    public String edit(
            @PathVariable String id,
            @ModelAttribute @Valid Country country,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "countries/edit";
        }
        repository.save(country);
        return "redirect:/countries/all";
    }

    @GetMapping("{id}/delete")
    public String deleteClient(@PathVariable int id) {
        repository.deleteById(id);
        return "redirect:/countries/all";
    }
}
