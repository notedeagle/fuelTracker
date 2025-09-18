package com.fueltracker.service;

import com.fueltracker.exception.ResourceNotFoundException;
import com.fueltracker.model.entity.Expense;
import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final VehicleService vehicleService;

    public List<Expense> getAllExpenses(long vehicleId) {
        return expenseRepository.findAllByVehicleId(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("No expenses found for vehicle id: " + vehicleId));
    }

    @Transactional
    public Expense addExpense(Expense expense, String vehicleName) {
        Objects.requireNonNull(expense, "Expense cannot be null");
        Objects.requireNonNull(vehicleName, "Vehicle name cannot be null");

        Vehicle vehicle = vehicleService.getCustomerVehicleByName(vehicleName);
        expense.setVehicle(vehicle);
        return expenseRepository.save(expense);
    }

    @Transactional
    public void deleteExpense(long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense with id " + id + " not found");
        }
        expenseRepository.deleteById(id);
    }
}
