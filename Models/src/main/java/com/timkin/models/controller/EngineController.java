package com.timkin.models.controller;

import com.timkin.models.entity.Engine;
import com.timkin.models.entity.EngineType;
import com.timkin.models.entity.Role;
import com.timkin.models.repo.EngineRepository;
import com.timkin.models.repo.EngineTypeRepository;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/engines")
public class EngineController {

    private final EngineRepository engineRepository;
    private final EngineTypeRepository typeRepository;

    public EngineController(EngineRepository engineRepository, EngineTypeRepository typeRepository) {
        this.engineRepository = engineRepository;
        this.typeRepository = typeRepository;
    }

    @GetMapping
    public String index() {
        return "redirect:/engines/all";
    }

    @GetMapping("/all")
    public String listEnginesAndTypes(Model model) {
        List<Engine> allEngines = engineRepository.findAll();
        model.addAttribute("engines", allEngines);
        List<EngineType> allEngineTypes = typeRepository.findAll();
        model.addAttribute("engine_types", allEngineTypes);
        return "engines/all_engines_and_types";
    }

    @GetMapping("/search")
    public String searchEngines(
            @RequestParam(name = "s") String searchString,
            Model model
    ) {
        List<Engine> filteredEngines = engineRepository.findByModelContainsIgnoreCase(searchString);
        model.addAttribute("engines", filteredEngines);
        List<EngineType> filteredTypes = typeRepository.findByNameContainsIgnoreCase(searchString);
        model.addAttribute("engine_types", filteredTypes);
        return "engines/all_engines_and_types";
    }

    @GetMapping("/{engine_id}")
    public String openEngine(
            @PathVariable("engine_id") int id
    ) {
        return "redirect:/engines/%d/info".formatted(id);
    }

    @GetMapping("/{engine_id}/info")
    public String openEngineInfo(
            @PathVariable("engine_id") int id,
            @ModelAttribute("info") Engine engine,
            Model model
    ) {
        Optional<Engine> found = engineRepository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/engines/all";
        }
        engine = found.get();
        model.addAttribute("info", engine);
        return "engines/engine_info";
    }

    @GetMapping("/add")
    public String openCreateEngine(
            @ModelAttribute Engine engine,
            Model model
    ) {
        List<EngineType> types = typeRepository.findAll();
        model.addAttribute("engine_types", types);
        return "engines/add_engine";
    }

    @PostMapping("/add")
    public String createEngine(
            @ModelAttribute @Valid Engine engine,
            BindingResult validationState,
            Model model
    ) {
        if (validationState.hasErrors()) {
            List<EngineType> engines = typeRepository.findAll();
            model.addAttribute("engine_types", engines);
            return "engines/add_engine";
        }
        engineRepository.save(engine);
        return "redirect:/engines/all";
    }

    @GetMapping("/{engine_id}/edit")
    public String openEditEngine(
            @PathVariable("engine_id") int id,
            @ModelAttribute Engine engine,
            Model model
    ) {
        Optional<Engine> found = engineRepository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/engines/all";
        }
        engine = found.get();
        model.addAttribute(engine);

        List<EngineType> types = typeRepository.findAll();
        model.addAttribute("engine_types", types);

        return "engines/edit_engine";
    }

    @PostMapping("/{engine_id}/edit")
    public String editEngine(
            @PathVariable("engine_id") int id,
            @ModelAttribute @Valid Engine engine,
            BindingResult validationState,
            Model model
    ) {
        if (validationState.hasErrors()) {
            List<EngineType> types = typeRepository.findAll();
            model.addAttribute("engine_types", types);
            return "engines/edit_engine";
        }
        engineRepository.save(engine);
        return "redirect:/engines/all";
    }

    @GetMapping("/{engine_id}/delete")
    public String deleteEngine(@PathVariable("engine_id") int id) {
        engineRepository.deleteById(id);
        return "redirect:/engines/all";
    }

    @GetMapping("/types/add")
    public String openAddType(@ModelAttribute("type") EngineType engineType) {
        return "engines/add_engine_type";
    }

    @PostMapping("/types/add")
    public String addType(
            @ModelAttribute("type") @Valid EngineType engineType,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "engines/add_engine_type";
        }
        typeRepository.save(engineType);
        return "redirect:/engines/all";
    }

    @GetMapping("/types/{type_id}/edit")
    public String openRenameType(
            @PathVariable("type_id") int id,
            @ModelAttribute("type") EngineType type,
            Model model
    ) {
        Optional<EngineType> found = typeRepository.findById(id);
        if (found.isEmpty()) {
            return "redirect:/engines/all";
        }
        type = found.get();
        model.addAttribute("type", type);
        return "engines/edit_engine_type";
    }

    @PostMapping("/types/{type_id}/edit")
    public String renameType(
            @PathVariable("type_id") int id,
            @ModelAttribute("type") @Valid EngineType type,
            BindingResult validationState
    ) {
        if (validationState.hasErrors()) {
            return "engines/edit_engine_type";
        }
        typeRepository.save(type);
        return "redirect:/engines/all";
    }

    @GetMapping("/types/{type_id}/delete")
    public String deleteType(@PathVariable("type_id") int id) {
        typeRepository.deleteById(id);
        return "redirect:/engines/all";
    }
}
