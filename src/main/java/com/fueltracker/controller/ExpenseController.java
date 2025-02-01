package com.fueltracker.controller;

import com.fueltracker.model.dto.ExpenseDto;
import com.fueltracker.model.entity.Expense;
import com.fueltracker.model.entity.Vehicles;
import com.fueltracker.service.ExpenseService;
import com.fueltracker.service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/expense")
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final VehicleService vehicleService;
    private final ModelMapper mapper;

    @GetMapping("/{vehicleName}")
    public List<ExpenseDto> findAllExpensesByVehicleName(@PathVariable String vehicleName) {
        Vehicles vehicle = vehicleService.getCustomerVehicleByName(vehicleName);

        return expenseService.getAllExpenses(vehicle.getId()).stream()
                .map(v -> mapper.map(v, ExpenseDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/{vehicleName}")
    public ExpenseDto addExpense(@RequestBody ExpenseDto expenseDto, @PathVariable String vehicleName) {
        Expense expense = mapper.map(expenseDto, Expense.class);
        return mapper.map(expenseService.addExpense(expense, vehicleName), ExpenseDto.class);
    }

    @Transactional
    @DeleteMapping("/{expenseId}")
    public void deleteExpense(@PathVariable long expenseId) {
        expenseService.deleteExpense(expenseId);
    }
}
