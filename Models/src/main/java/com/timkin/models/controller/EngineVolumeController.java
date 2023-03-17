package com.timkin.models.controller;

import com.timkin.models.entity.EngineVolume;
import com.timkin.models.repo.EngineVolumeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("volumes")
public class EngineVolumeController {
    private final EngineVolumeRepository repository;

    public EngineVolumeController(EngineVolumeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index() {
        return "redirect:/volumes/all";
    }

    @GetMapping("/all")
    public String openAll(Model model) {
        List<EngineVolume> all = repository.findAll();
        model.addAttribute("volumes", all);
        return "volumes/all";
    }

    @GetMapping("/add")
    public String openAdd(@ModelAttribute("volume") EngineVolume volume) {
        return "volumes/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("volume") @Valid EngineVolume volume,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "volumes/add";
        }

        repository.save(volume);
        return "redirect:/volumes/all";
    }

    @GetMapping("{id}/edit")
    public String openEdit(
            @PathVariable int id,
            Model model
    ) {
        Optional<EngineVolume> volume = repository.findById(id);
        if (volume.isEmpty()) {
            return "redirect:/volumes/all";
        }
        model.addAttribute("volume", volume.get());
        return "volumes/edit";
    }

    @PostMapping("{id}/edit")
    public String edit(
            @PathVariable String id,
            @ModelAttribute @Valid EngineVolume volume,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "volumes/edit";
        }
        repository.save(volume);
        return "redirect:/volumes/all";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable int id) {
        repository.deleteById(id);
        return "redirect:/volumes/all";
    }
}
