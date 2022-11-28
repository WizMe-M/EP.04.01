package com.timkin.models.controller;

import com.timkin.models.entity.Motorcycle;
import com.timkin.models.repo.MotorcycleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/search")
    public String searchBikes(
            @RequestParam(name = "s") String searchString,
            Model model
    ) {
        List<Motorcycle> filtered = repository.findByModelContainsIgnoreCase(searchString);
        model.addAttribute("bikes", filtered);
        return "motorcycles/all_bikes";
    }

    @GetMapping("/add")
    public String openAddBikePage(Motorcycle motorcycle) {
        return "motorcycles/add_new_bike";
    }

    @PostMapping("/add")
    public String addBike(
            @Valid Motorcycle motorcycle,
            BindingResult validState
    ) {
        if (validState.hasErrors()) {
            return "motorcycles/add_new_bike";
        }
        repository.save(motorcycle);
        return "redirect:/motorcycles/all";
    }

    @GetMapping("/details/{id}")
    public String openDetails(
            @PathVariable int id,
            Model model,
            Motorcycle motorcycle
    ) {
        Optional<Motorcycle> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/motorcycles/all";
        }

        motorcycle = found.get();
        model.addAttribute("motorcycle", motorcycle);
        return "motorcycles/motorcycle_details";
    }

    @GetMapping("/details/{id}/edit")
    public String openEditDetails(
            @PathVariable int id,
            @ModelAttribute(name = "details") Motorcycle motorcycle,
            Model model
    ) {
        Optional<Motorcycle> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/motorcycles/all";
        }
        motorcycle = found.get();
        model.addAttribute("details", motorcycle);
        return "motorcycles/edit_details";
    }

    @PostMapping("/details/{id}/edit")
    public String saveChangedDetails(
            @PathVariable int id,
            @ModelAttribute(name = "details") @Valid Motorcycle motorcycle,
            BindingResult validState
    ) {
        Optional<Motorcycle> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/motorcycles/all";
        }

        if (validState.hasErrors()) {
            return "motorcycles/edit_details";
        }

        repository.save(motorcycle);
        return String.format("redirect:/motorcycles/details/%d", motorcycle.getId());
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
