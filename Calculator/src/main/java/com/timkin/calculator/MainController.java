package com.timkin.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping
    public String index() {
        return "redirect:/calc";
    }

    @GetMapping("/calc")
    public String openCalculator() {
        return "/calc";
    }

    @GetMapping("/calculate")
    public String calculateGet(@RequestParam double a, @RequestParam double b, @RequestParam String op, Model model) {
        return calculate(a, b, op, model);
    }

    @PostMapping("/calculate")
    public String calculatePost(@RequestParam double a, @RequestParam double b, @RequestParam String op, Model model) {
        return calculate(a, b, op, model);
    }

    private String calculate(@RequestParam double a, @RequestParam double b, @RequestParam String op, Model model) {
        String operationSign = op.toUpperCase();

        Calculator operation = switch (operationSign) {
            case "SUM" -> Calculator.SUM;
            case "SUBTRACT" -> Calculator.SUBTRACT;
            case "MULTIPLY" -> Calculator.MULTIPLY;
            case "DIVIDE" -> Calculator.DIVIDE;
            default -> throw new IllegalArgumentException("Argument 'op' was not in correct format");
        };

        double result = operation.executeBinaryOperation(a, b);
        model.addAttribute("res", result);
        return "/result";
    }
}
