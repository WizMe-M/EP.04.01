package com.timkin.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping
    public String calculate(int a, int b, String op) {
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
