package com.fueltracker.utils;

import com.fueltracker.model.dto.CostPerMonth;
import com.fueltracker.model.entity.Expenses;
import com.fueltracker.model.entity.Refuels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CostCalculator {
    private final DistanceCalculator distanceCalculator = new DistanceCalculator();

    public BigDecimal calculateTotalCost(List<Refuels> refuels, List<Expenses> expenses) {
        BigDecimal refuelCost = refuels.stream()
                .map(Refuels::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenseCost = expenses.stream()
                .map(Expenses::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return refuelCost.add(expenseCost);
    }

    public BigDecimal calculateTotalCostPerDay(List<Refuels> refuels, List<Expenses> expenses) {
        BigDecimal totalCost = calculateTotalCost(refuels, expenses);
        return totalCost.divide(distanceCalculator.calculateAmountOfDays(refuels, expenses), RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalCostPerKm(List<Refuels> refuels, List<Expenses> expenses) {
        return calculateTotalCost(refuels, expenses).divide(BigDecimal.valueOf(distanceCalculator.calculateTotalDistance(refuels, expenses)),
                RoundingMode.HALF_UP);
    }

    public Set<CostPerMonth> getCostPerMonth(List<Refuels> refuels, List<Expenses> expenses) {
        Set<CostPerMonth> costPerMonthSet = new HashSet<>();
        Set<Integer> month = new HashSet<>();

        refuels.forEach(refuel -> month.add(refuel.getDate().getMonthValue()));
        expenses.forEach(expense -> month.add(expense.getDate().getMonthValue()));

        month.forEach(monthNumber -> costPerMonthSet.add(CostPerMonth.builder()
                .monthNumber(monthNumber)
                .totalCost(getTotalCostPerMonth(refuels, expenses, monthNumber))
                .build()));

        return costPerMonthSet;
    }

    private BigDecimal getTotalCostPerMonth(List<Refuels> refuels, List<Expenses> expenses, int monthNumber) {
        BigDecimal totalCostRefuels = refuels.stream()
                .filter(c -> c.getDate().getMonthValue() == monthNumber)
                .map(Refuels::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCostExpense = expenses.stream()
                .filter(c -> c.getDate().getMonthValue() == monthNumber)
                .map(Expenses::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalCostRefuels.add(totalCostExpense);
    }
}
