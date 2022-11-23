package com.timkin.models.controller;

import com.timkin.models.entity.Motorcycle;
import com.timkin.models.repo.MotorcycleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/add")
    public String openAddBikePage() {
        return "motorcycles/add_new_bike";
    }

    @PostMapping("/add")
    public String addBike(
            @RequestParam String model,
            @RequestParam double price,
            @RequestParam(name = "engine-volume") double engineVolume,
            @RequestParam(name = "engine-type") String engineType,
            @RequestParam boolean sold
    ) {
        Motorcycle motorcycle = new Motorcycle(model, price, sold, engineVolume, engineType);
        repository.save(motorcycle);
        return "redirect:/motorcycles/all";
    }
}
