package com.example.budgettrackerspring;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Entity class - DB Table
@Entity
public class BudgetEntry {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    public Long getId() { return id; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
