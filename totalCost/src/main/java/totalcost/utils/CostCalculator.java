package totalcost.utils;


import persistance.dto.CostPerMonth;
import persistance.entity.Expense;
import persistance.entity.Refuel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CostCalculator {
    private final DistanceCalculator distanceCalculator = new DistanceCalculator();

    public BigDecimal calculateTotalCost(List<Refuel> refuels, List<Expense> expenses) {
        BigDecimal refuelCost = refuels.stream()
                .map(Refuel::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenseCost = expenses.stream()
                .map(Expense::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return refuelCost.add(expenseCost);
    }

    public BigDecimal calculateTotalCostPerDay(List<Refuel> refuels, List<Expense> expenses) {
        BigDecimal totalCost = calculateTotalCost(refuels, expenses);
        return totalCost.divide(distanceCalculator.calculateAmountOfDays(refuels, expenses), RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalCostPerKm(List<Refuel> refuels, List<Expense> expenses) {
        return calculateTotalCost(refuels, expenses).divide(BigDecimal.valueOf(distanceCalculator.calculateTotalDistance(refuels, expenses)),
                RoundingMode.HALF_UP);
    }

    public Set<CostPerMonth> getCostPerMonth(List<Refuel> refuels, List<Expense> expenses) {
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

    private BigDecimal getTotalCostPerMonth(List<Refuel> refuels, List<Expense> expenses, int monthNumber) {
        BigDecimal totalCostRefuels = refuels.stream()
                .filter(c -> c.getDate().getMonthValue() == monthNumber)
                .map(Refuel::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCostExpense = expenses.stream()
                .filter(c -> c.getDate().getMonthValue() == monthNumber)
                .map(Expense::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalCostRefuels.add(totalCostExpense);
    }
}
