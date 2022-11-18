package com.timkin.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/calc")
    public String openCalculator() {
        return "calc";
    }

    @PostMapping
    public String calculate() {

        return "redirect:/calc";
    }
}
