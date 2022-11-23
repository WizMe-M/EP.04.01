package com.timkin.models.controller;

import com.timkin.models.entity.Motorcycle;
import com.timkin.models.repo.MotorcycleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/motorcycles")
public class MotorcycleController {
    private final MotorcycleRepository repository;

    public MotorcycleController(MotorcycleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String openAllBikesTable(Model model) {
        List<Motorcycle> all = repository.findAll();
        model.addAttribute("bikes", all);
        return "motorcycles/all_bikes";
    }
}
