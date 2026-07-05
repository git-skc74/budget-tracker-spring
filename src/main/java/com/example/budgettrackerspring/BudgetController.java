package com.example.budgettrackerspring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// initialize this class is the controller that takes HTTP requests
@RestController
public class BudgetController {

    @GetMapping("/hello") // execute this method when GET /hello requested
    public String hello() {
        return "hello"; // return the text "hello" to the browser
    }

    // Budget Book ArrayList
    private List<Integer> book = new ArrayList<>();

    @PostMapping("/entry") // return?
    public String addEntry(@RequestParam int amount) {
        // execute when requested: POST /entry?amount=INT
        // @RequestParam takes the vall=ue of amount in url as int
        book.add(amount);
        return "added: " + amount;
    }

    @GetMapping("/history")
    public List<Integer> getHistory() {
        // return the entire list of book when requested: GET /history
        // Spring automatically converts returned List to JSON
        return book;
    }
}