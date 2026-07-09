package com.example.budgettrackerspring;

import org.springframework.beans.factory.annotation.Autowired;
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
    // repository using MySQL
    private final BudgetRepository repository;

    public BudgetController(BudgetRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hello") // execute this method when GET /hello requested
    public String hello() {
        return "hello"; // return the text "hello" to the browser
    }

    @PostMapping("/entry") // execute when requested: POST /entry?amount=INT
    public String addEntry(@RequestParam int amount) {
        // @RequestParam takes the value of amount in url as int
        BudgetEntry entry = new BudgetEntry();
        entry.setAmount(amount);
        repository.save(entry);

        return "added: " + amount;
    }

    @GetMapping("/history")
    public Map<String, Object> getHistory() {
        // return the entire list of book when requested: GET /history
        // Spring automatically converts returned List to JSON

        // findAll returns all data in List<BudgetEntry>
        List<BudgetEntry> entries = repository.findAll();

        // HashMap response
        Map<String, Object> response = new HashMap<>();
        response.put("history", classifyTransaction(entries));
        response.put("total", totalBook(entries));
        return response;
    }

    private List<String> classifyTransaction(List<BudgetEntry> entries) {
        List<String> newBook = new ArrayList<>();
        for (BudgetEntry entry : entries) {
            int amount = entry.getAmount();
            if (amount > 0) {
                newBook.add("Income: " + amount);
            } else if (amount < 0) {
                newBook.add("Expense: " + Math.abs(amount));
            }
        }
        return newBook;
    }

    private int totalBook(List<BudgetEntry> entries) {
        int total = 0;
        for (BudgetEntry entry : entries) {
            total += entry.getAmount();
        }
        return total;
    }
}