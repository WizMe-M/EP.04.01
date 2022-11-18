package com.timkin.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
public class CalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }
}
