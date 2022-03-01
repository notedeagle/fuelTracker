package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.dto.AllCustomerCostDto;
import com.fuel.tracker.fueltracker.model.entity.Expense;
import com.fuel.tracker.fueltracker.model.entity.Refuel;
import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.utility.CostCalculator;
import com.fuel.tracker.fueltracker.utility.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record TotalCostService(CostCalculator costCalculator, DistanceCalculator distanceCalculator,
                               VehicleService vehicleService, RefuelService refuelService, ExpenseService expenseService) {
    public AllCustomerCostDto getTotalCost() {
        List<Vehicle> vehicles = vehicleService.getAllUserVehicles();
        List<Expense> expenses = new ArrayList<>();
        List<Refuel> refuels = new ArrayList<>();

        vehicles.forEach(vehicle -> {
            refuels.addAll(refuelService.getAllCarRefuel(vehicle.getId()));
            expenses.addAll(expenseService.getAllExpenses(vehicle.getId()));
        });

        return AllCustomerCostDto.builder()
                .totalCost(costCalculator.calculateTotalCost(refuels, expenses))
                .costPerDay(costCalculator.calculateTotalCostPerDay(refuels, expenses))
                .costPerKm(costCalculator.calculateTotalCostPerKm(refuels, expenses))
                .costPerMonthList(costCalculator.calculateCostPerMonth(refuels, expenses))
                .totalDistance(distanceCalculator.calculateTotalDistance(refuels, expenses))
                .distancePerDay(distanceCalculator.calculateDistancePerDay(refuels, expenses))
                .build();
    }
}
