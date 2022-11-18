package com.timkin.calculator;

import org.springframework.stereotype.Controller;
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
        return "calc";
    }

    @PostMapping("/calc")
    public String calculate(@RequestParam double a, @RequestParam double b, @RequestParam String op) {
        String operationSign = op.toUpperCase();

        Calculator operation = switch (operationSign) {
            case "SUM" -> Calculator.SUM;
            case "SUBTRACT" -> Calculator.SUBTRACT;
            case "MULTIPLY" -> Calculator.MULTIPLY;
            case "DIVIDE" -> Calculator.DIVIDE;
            default -> throw new IllegalArgumentException("Argument 'op' was not in correct format");
        };

        double result = operation.executeBinaryOperation(a, b);
        return "redirect:/calc";
    }
}
