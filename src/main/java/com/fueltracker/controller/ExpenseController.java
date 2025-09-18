package com.fueltracker.controller;

import com.fueltracker.model.dto.ExpenseDto;
import com.fueltracker.model.entity.Expense;
import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.service.ExpenseService;
import com.fueltracker.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/expenses")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Expense Controller", description = "API for managing vehicle expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final VehicleService vehicleService;
    private final ModelMapper mapper;

    @GetMapping("/{vehicleName}")
    @Operation(summary = "Get all expenses for a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved expenses"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<List<ExpenseDto>> findAllExpensesByVehicleName(@PathVariable String vehicleName) {
        Vehicle vehicle = vehicleService.getCustomerVehicleByName(vehicleName);
        List<ExpenseDto> expenses = expenseService.getAllExpenses(vehicle.getId()).stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/{vehicleName}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add an expense record for a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Expense record created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<ExpenseDto> addExpense(@Valid @RequestBody ExpenseDto expenseDto, @PathVariable String vehicleName) {
        Expense expense = convertToEntity(expenseDto);
        Expense savedExpense = expenseService.addExpense(expense, vehicleName);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedExpense));
    }

    @DeleteMapping("/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an expense record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Expense record deleted"),
        @ApiResponse(responseCode = "404", description = "Expense record not found")
    })
    public ResponseEntity<Void> deleteExpense(@PathVariable long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }

    private ExpenseDto convertToDto(Expense expense) {
        return mapper.map(expense, ExpenseDto.class);
    }

    private Expense convertToEntity(ExpenseDto expenseDto) {
        return mapper.map(expenseDto, Expense.class);
    }
}
