package com.timkin.models.controller;

import com.timkin.models.entity.Consumer;
import com.timkin.models.entity.Country;
import com.timkin.models.repo.ConsumerRepository;
import com.timkin.models.repo.CountryRepository;
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
    private final CountryRepository countryRepository;

    public ConsumerController(ConsumerRepository repository,
                              CountryRepository countryRepository) {
        this.repository = repository;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public String index() {
        return "redirect:/consumers/all";
    }

    @GetMapping("/all")
    public String openAll(Model model) {
        List<Consumer> all = repository.findAll();
        model.addAttribute("consumers", all);
        return "consumers/all";
    }

    @GetMapping("{id}")
    public String open(
            @PathVariable int id,
            Model model
    ) {
        Optional<Consumer> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/consumers/all";
        }

        Consumer consumer = found.get();
        model.addAttribute("consumer", consumer);
        return "consumers/details";
    }

    @GetMapping("/add")
    public String openAdd(
            @ModelAttribute Consumer consumer,
            Model model
    ) {
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "consumers/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute @Valid Consumer consumer,
            BindingResult validationState,
            Model model
    ) {
        if (validationState.hasErrors()) {
            List<Country> countries = countryRepository.findAll();
            model.addAttribute("countries", countries);
            return "consumers/add";
        }

        repository.save(consumer);
        return "redirect:/consumers/all";
    }

    @GetMapping("{id}/edit")
    public String openEdit(
            @PathVariable int id,
            Model model
    ) {
        List<Country> countries = countryRepository.findAll();
        Optional<Consumer> consumer = repository.findById(id);
        if (consumer.isEmpty()) {
            return "redirect:/consumers/all";
        }
        model.addAttribute("consumer", consumer.get());
        model.addAttribute("countries", countries);
        return "consumers/edit";
    }

    @PostMapping("{id}/edit")
    public String edit(
            @PathVariable String id,
            @ModelAttribute @Valid Consumer consumer,
            BindingResult validationState,
            Model model
    ) {
        if (validationState.hasErrors()) {
            List<Country> countries = countryRepository.findAll();
            model.addAttribute("countries", countries);
            return "consumers/edit";
        }
        repository.save(consumer);
        return "redirect:/consumers/all";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable int id) {
        repository.deleteById(id);
        return "redirect:/consumers/all";
    }
}
