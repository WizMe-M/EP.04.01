package com.timkin.models.controller;

import com.timkin.models.repo.EngineRepository;
import com.timkin.models.repo.EngineTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listEnginesAndTypes() {
        return "engines/all_engines_and_types";
    }
}
