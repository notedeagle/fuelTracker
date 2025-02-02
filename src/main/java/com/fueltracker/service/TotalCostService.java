package com.fueltracker.service;

import com.fueltracker.model.dto.AllCustomerCostDto;
import com.fueltracker.model.dto.Dates;
import com.fueltracker.model.entity.Expenses;
import com.fueltracker.model.entity.Refuels;
import com.fueltracker.model.entity.Vehicles;
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
        Vehicles vehicle = vehicleService.getCustomerVehicleByName(vehicleName);

        List<Refuels> refuels = new ArrayList<>(refuelService.getAllCarRefuel(vehicle.getId()));
        List<Expenses> expenses = new ArrayList<>(expenseService.getAllExpenses(vehicle.getId()));
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
