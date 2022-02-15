package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.entity.Expense;
import com.fuel.tracker.fueltracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ExpenseService(ExpenseRepository expenseRepository,
                             VehicleService vehicleService) {

    public List<Expense> getAllExpenses(long vehicleId) {
        return expenseRepository.findAllByVehicleId(vehicleId).orElseThrow(IllegalStateException::new);
    }

    public Expense addExpense(Expense expense, String vehicleName) {
        expense.setVehicle(vehicleService.getVehicleByName(vehicleName));
        return expenseRepository.save(expense);
    }

    public void deleteExpense(long id) {
        expenseRepository.deleteById(id);
    }
}
