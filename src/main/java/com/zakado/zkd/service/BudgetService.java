package com.zakado.zkd.service;

import com.zakado.zkd.model.Budget;

import java.util.List;

public interface BudgetService {
    Budget saveBudget(Budget budget);
    void updateBudget(Budget budget);

    Budget searchBudgetByIdMovie(int id);

    List<Budget> getAllBudget();

    void deleteBudget(Budget budget);
}
