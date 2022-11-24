package com.timkin.models.controller;

import com.timkin.models.entity.Motorcycle;
import com.timkin.models.entity.User;
import com.timkin.models.repo.MotorcycleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/motorcycles")
public class MotorcycleController {
    private final MotorcycleRepository repository;

    public MotorcycleController(MotorcycleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/motorcycles/all";
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

    @GetMapping("/details/{id}")
    public String openDetails(
            @PathVariable int id,
            Model model
    ) {
        Optional<Motorcycle> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/motorcycles/all";
        }

        Motorcycle motorcycle = found.get();
        model.addAttribute("motorcycle", motorcycle);
        return "motorcycles/motorcycle_details";
    }

    @GetMapping("/details/{id}/edit")
    public String openEditDetails(
            @PathVariable int id,
            Motorcycle details
    ) {
        Optional<Motorcycle> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/motorcycles/all";
        }

        details = found.get();
        return "motorcycles/edit_details";
    }

    @PostMapping("/details/{id}/edit")
    public String saveChangedDetails(
            @PathVariable int id,
            Motorcycle details
    ) {
        repository.save(details);
        return "motorcycles/motorcycle_details";
    }

    @GetMapping("/delete-motorcycle/{id}")
    public String deleteMotorcycle(
            @PathVariable int id
    ) {
        Optional<Motorcycle> found = repository.findById(id);
        if (found.isPresent()) {
            Motorcycle deleting = found.get();
            repository.delete(deleting);
        }
        return "redirect:/motorcycles/all";
    }
}
