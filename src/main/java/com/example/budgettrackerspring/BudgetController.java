package com.example.budgettrackerspring;

import org.springframework.web.bind.annotation.*;

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
    public String addEntry(@RequestParam int amount,
                           @RequestParam(required = false) String category) {
        // @RequestParam takes the value of amount in url as int
        BudgetEntry entry = new BudgetEntry();
        entry.setAmount(amount);
        if (category != null && !category.isEmpty()) {
            entry.setCategory(category);
        }
        repository.save(entry);

        return "added: " + amount + "\ncategory: " + category;
    }

    @DeleteMapping("/entry/{id}")
    public String deleteEntry(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "deleted: " + id;
        }
        else {
            return "not found: " + id;
        }
    }

    @PutMapping("/entry/{id}")
    public String updateEntry(@PathVariable Long id,
                              @RequestParam(required = false) Integer amount,
                              @RequestParam(required = false) String category) {
        // use Integer for null check
        // StringBuilder - using append
        StringBuilder sb = new StringBuilder("requested id: " + id + "\n");
        repository.findById(id).ifPresentOrElse(
                // if data (= entry) presents
                entry -> {
                    if (amount != null) {
                        entry.setAmount(amount);
                        sb.append("amount updated: ").append(amount).append("\n");
                    } else { sb.append("no amount entered\n"); }
                    if (category != null && !category.isEmpty()) {
                        entry.setCategory(category);
                        sb.append("category updated: ").append(category);
                    } else { sb.append("no category entered"); }
                    repository.save(entry);
                },

                // if data does not exist
                () -> {
                    sb.setLength(0); // empty StringBuilder
                    sb.append("not found: ").append(id);
                }
        );
        return sb.toString();
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
            String category = entry.getCategory();
            // display only if category exists
            String categoryStr = (category != null) ? " [" + category + "]" : "";
            if (amount > 0) {
                newBook.add("Income: " + amount + categoryStr);
            } else if (amount < 0) {
                newBook.add("Expense: " + Math.abs(amount) + categoryStr);
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