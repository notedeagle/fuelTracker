package com.fueltracker.service;

import com.fueltracker.model.dto.AllCustomerCostDto;
import com.fueltracker.model.dto.Dates;
import com.fueltracker.model.entity.Expense;
import com.fueltracker.model.entity.Refuel;
import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.utils.CostCalculator;
import com.fueltracker.utils.DateCalculator;
import com.fueltracker.utils.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record TotalCostService(CostCalculator costCalculator, DistanceCalculator distanceCalculator, VehicleService vehicleService,
                               RefuelService refuelService, ExpenseService expenseService, DateCalculator dateCalculator) {
    public AllCustomerCostDto getTotalCost(String vehicleName) {
        Vehicle vehicle = vehicleService.getCustomerVehicleByName(vehicleName);

        List<Refuel> refuels = new ArrayList<>(refuelService.getAllCarRefuel(vehicle.getId()));
        List<Expense> expenses = new ArrayList<>(expenseService.getAllExpenses(vehicle.getId()));
        Dates dates = dateCalculator.startAndEndDateCalculator(refuels, expenses);

        return AllCustomerCostDto.builder()
                .startDate(dates.getStartDate())
                .endDate(dates.getEndDate())
                .totalCost(costCalculator.calculateTotalCost(refuels, expenses))
                .costPerDay(costCalculator.calculateTotalCostPerDay(refuels, expenses))
                .costPerKm(costCalculator.calculateTotalCostPerKm(refuels, expenses))
                .costPerMonth(costCalculator.getCostPerMonth(refuels, expenses))
                .totalDistance(distanceCalculator.calculateTotalDistance(refuels, expenses))
                .distancePerDay(distanceCalculator.calculateDistancePerDay(refuels, expenses))
                .build();
    }
}
