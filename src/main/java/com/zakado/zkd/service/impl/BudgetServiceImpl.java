package com.zakado.zkd.service.impl;

import com.zakado.zkd.model.Budget;
import com.zakado.zkd.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private static final String URL = "http://localhost:8080/api/budget";
    private final RestTemplate template;
    @Override
    public Budget saveBudget(Budget budget) {
       return template.postForObject(URL, budget, Budget.class);
    }

    @Override
    public void updateBudget(Budget budget) {
        template.postForObject(URL, budget, Budget.class);
    }

    @Override
    public Budget searchBudgetByIdMovie(int id) {
        return template.getForObject(URL + "/" + id, Budget.class);
    }

    @Override
    public List<Budget> getAllBudget() {
        return Arrays.asList(Objects.requireNonNull(template.getForObject(URL, Budget[].class)));
    }

    @Override
    public void deleteBudget(Budget budget) {
        template.delete(URL + "/" + budget.getId());
    }
}
