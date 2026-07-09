package com.example.budgettrackerspring;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<BudgetEntry, Long> {
}
