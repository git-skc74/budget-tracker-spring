package com.example.budgettrackerspring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// initialize this class is the controller that takes HTTP requests
@RestController
public class BudgetController {

    @GetMapping("/hello") // execute this method when GET /hello requested
    public String hello() {
        return "hello"; // return the text "hello" to the browser
    }
}