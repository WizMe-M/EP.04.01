package com.timkin.models.controller;

import com.timkin.models.entity.Engine;
import com.timkin.models.entity.Motorcycle;
import com.timkin.models.entity.MotorcycleType;
import com.timkin.models.repo.EngineRepository;
import com.timkin.models.repo.MotorcycleRepository;
import com.timkin.models.repo.MotorcycleTypeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/motorcycles")
@PreAuthorize("hasAnyAuthority('Technic')")
public class MotorcycleController {
    private final MotorcycleRepository repository;
    private final EngineRepository engineRepository;
    private final MotorcycleTypeRepository typeRepository;

    public MotorcycleController(MotorcycleRepository repository, EngineRepository engineRepository,
                                MotorcycleTypeRepository typeRepository) {
        this.repository = repository;
        this.engineRepository = engineRepository;
        this.typeRepository = typeRepository;
    }

    @GetMapping
    public String index() {
        return "redirect:/motorcycles/all";
    }

    @GetMapping("/all")
    public String openAllBikesTable(
            @ModelAttribute("type") MotorcycleType motorcycleType,
            Model model
    ) {
        List<Motorcycle> motorcycles = repository.findAll();
        List<MotorcycleType> types = typeRepository.findAll();
        model.addAttribute("bikes", motorcycles);
        model.addAttribute("types", types);
        return "motorcycles/all_bikes";
    }

    @GetMapping("/search")
    public String searchBikes(
            @ModelAttribute("type") MotorcycleType motorcycleType,
            @RequestParam(name = "s") String searchString,
            Model model
    ) {
        List<Motorcycle> filtered = repository.findByModelContainsIgnoreCase(searchString);
        List<MotorcycleType> types = typeRepository.findAll();
        model.addAttribute("bikes", filtered);
        model.addAttribute("types", types);
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
        List<MotorcycleType> types = typeRepository.findAll();
        List<Engine> allEngines = engineRepository.findAll();
        model.addAttribute("engines", allEngines);
        model.addAttribute("types", types);
        return "motorcycles/add_new_bike";
    }

    @PostMapping("/add")
    public String addMotorcycle(
            @ModelAttribute @Valid Motorcycle motorcycle,
            BindingResult validationState,
            Model model
    ) {
        if (validationState.hasErrors()) {
            List<MotorcycleType> types = typeRepository.findAll();
            List<Engine> allEngines = engineRepository.findAll();
            model.addAttribute("engines", allEngines);
            model.addAttribute("types", types);
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
        List<MotorcycleType> types = typeRepository.findAll();
        List<Engine> allEngines = engineRepository.findAll();
        model.addAttribute("engines", allEngines);
        model.addAttribute("types", types);
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
            List<MotorcycleType> types = typeRepository.findAll();
            List<Engine> allEngines = engineRepository.findAll();
            model.addAttribute("engines", allEngines);
            model.addAttribute("types", types);
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

    @PostMapping("/add-type")
    public String addType(
            @Valid @ModelAttribute("type") MotorcycleType motorcycleType,
            BindingResult validationState,
            Model model
    ) {
        if (validationState.hasErrors()) {
            List<Motorcycle> all = repository.findAll();
            model.addAttribute("bikes", all);
            return "motorcycles/all_bikes";
        }

        typeRepository.save(motorcycleType);
        return "redirect:/motorcycles";
    }

    @GetMapping("/edit-type/{id}")
    public String openEditType(
            @PathVariable int id,
            Model model
    ) {
        Optional<MotorcycleType> found = typeRepository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/motorcycles";
        }
        model.addAttribute("type", found.get());
        return "motorcycles/edit_motorcycle_type";
    }

    @PostMapping("/edit-type/{id}")
    public String editType(
            @PathVariable int id,
            @Valid @ModelAttribute("type") MotorcycleType motorcycleType,
            BindingResult validationResult
    ) {
        if (validationResult.hasErrors()) {
            return "motorcycles/edit_motorcycle_type";
        }

        typeRepository.save(motorcycleType);
        return "redirect:/motorcycles";
    }

    @GetMapping("/delete-type/{id}")
    public String deleteType(@PathVariable int id) {
        Optional<MotorcycleType> found = typeRepository.findById(id);
        if (found.isPresent()) {
            typeRepository.deleteById(id);
        }
        return "redirect:/motorcycles";
    }
}
