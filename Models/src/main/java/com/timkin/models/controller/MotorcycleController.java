package com.timkin.models.controller;

import com.timkin.models.entity.Engine;
import com.timkin.models.entity.Motorcycle;
import com.timkin.models.repo.EngineRepository;
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
    private final EngineRepository engineRepository;

    public MotorcycleController(MotorcycleRepository repository, EngineRepository engineRepository) {
        this.repository = repository;
        this.engineRepository = engineRepository;
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

    @GetMapping("{id}")
    public String openMotorcycle(@PathVariable int id) {
        return "redirect:/motorcycles/details/%d".formatted(id);
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

    @GetMapping("/add")
    public String openAddMotorcycle(
            @ModelAttribute Motorcycle motorcycle,
            Model model) {
        List<Engine> allEngines = engineRepository.findAll();
        model.addAttribute("engines", allEngines);
        return "motorcycles/add_new_bike";
    }

    @PostMapping("/add")
    public String addMotorcycle(
            @ModelAttribute @Valid Motorcycle motorcycle,
            BindingResult validationState,
            Model model
    ) {
        if (validationState.hasErrors()) {
            List<Engine> allEngines = engineRepository.findAll();
            model.addAttribute("engines", allEngines);
            return "motorcycles/add_new_bike";
        }
        repository.save(motorcycle);
        return "redirect:/motorcycles/all";
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
        List<Engine> allEngines = engineRepository.findAll();
        model.addAttribute("engines", allEngines);
        return "motorcycles/edit_details";
    }

    @PostMapping("/details/{id}/edit")
    public String saveChangedDetails(
            @PathVariable int id,
            @ModelAttribute(name = "details") @Valid Motorcycle motorcycle,
            BindingResult validationState,
            Model model
    ) {
        Optional<Motorcycle> found = repository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/motorcycles/all";
        }

        if (validationState.hasErrors()) {
            List<Engine> allEngines = engineRepository.findAll();
            model.addAttribute("engines", allEngines);
            return "motorcycles/edit_details";
        }

        repository.save(motorcycle);
        return String.format("redirect:/motorcycles/details/%d", motorcycle.getId());
    }

    @GetMapping("/{id}/delete")
    public String deleteMotorcycle(@PathVariable int id) {
        return "redirect:/motorcycles/details/%d/delete".formatted(id);
    }

    @GetMapping("/details/{id}/delete")
    public String deleteMotorcycleDetails(@PathVariable int id) {
        Optional<Motorcycle> found = repository.findById(id);
        if (found.isPresent()) {
            Motorcycle deleting = found.get();
            repository.delete(deleting);
        }
        return "redirect:/motorcycles/all";
    }
}
