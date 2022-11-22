package com.timkin.models.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String openHomePage() {
        return "main/index";
    }
}
