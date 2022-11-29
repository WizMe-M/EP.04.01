package com.timkin.models.controller;

import com.timkin.models.entity.Engine;
import com.timkin.models.entity.EngineType;
import com.timkin.models.repo.EngineRepository;
import com.timkin.models.repo.EngineTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        model.addAttribute("engineTypes", allEngineTypes);
        return "engines/all_engines_and_types";
    }

    @GetMapping("/search")
    public String searchEngines(
            @RequestParam(name = "s") String searchString,
            Model model
    ) {
        List<Engine> filtered = engineRepository.findByModelContainsIgnoreCase(searchString);
        model.addAttribute("engines", filtered);
        return "engines/all_engines_and_types";
    }

    @GetMapping("/add")
    public String createEngine(
            @ModelAttribute Engine engine,
            Model model
    ) {
        List<EngineType> types = typeRepository.findAll();
        model.addAttribute("engine_types", types);
        return "engines/add_engine";
    }
}
