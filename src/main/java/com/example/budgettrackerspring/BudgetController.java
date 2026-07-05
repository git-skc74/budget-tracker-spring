package com.example.budgettrackerspring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// initialize this class is the controller that takes HTTP requests
@RestController
public class BudgetController {

    @GetMapping("/hello") // execute this method when GET /hello requested
    public String hello() {
        return "hello"; // return the text "hello" to the browser
    }

    // Budget Book ArrayList
    private List<Integer> book = new ArrayList<>();

    @PostMapping("/entry") // execute when requested: POST /entry?amount=INT
    public String addEntry(@RequestParam int amount) {
        // @RequestParam takes the vall=ue of amount in url as int
        book.add(amount);
        return "added: " + amount;
    }

    @GetMapping("/history")
    public Map<String, Object> getHistory() {
        // return the entire list of book when requested: GET /history
        // Spring automatically converts returned List to JSON

        // HashMap response
        Map<String, Object> response = new HashMap<>();
        response.put("history", classifyTransaction(book));
        response.put("total", totalBook(book));

        return response;
    }

    private List<String> classifyTransaction(List<Integer> book) {
        List<String> newBook = new ArrayList<>();

        for (int amount : book) {
            if (amount > 0) {
                newBook.add("Income: " + amount);
            } else if (amount < 0) {
                newBook.add("Expense: " + Math.abs(amount));
            }
        }
        return newBook;
    }

    private int totalBook(List<Integer> book) {
        int total = 0;
        for (int i : book) {
            total += i;
        }
        return total;
    }
}