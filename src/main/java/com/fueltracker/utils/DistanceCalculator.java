package com.fueltracker.utils;

import com.fueltracker.model.entity.Expense;
import com.fueltracker.model.entity.Refuels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DistanceCalculator {

    public long calculateTotalDistance(List<Refuels> refuels, List<Expense> expenses) {
        List<Integer> distances = new ArrayList<>();

        refuels.forEach(refuel -> distances.add(refuel.getOdometer()));
        expenses.forEach(expense -> distances.add(expense.getOdometer()));
        Collections.sort(distances);

        return distances.get(distances.size() - 1) - distances.get(0);
    }

    public BigDecimal calculateDistancePerDay(List<Refuels> refuels, List<Expense> expenses) {
        long totalDistance = calculateTotalDistance(refuels, expenses);

        return BigDecimal.valueOf(totalDistance).divide(calculateAmountOfDays(refuels, expenses), RoundingMode.HALF_UP);
    }

    public long calculateTotalDistance(List<Refuels> refuels) {
        List<Integer> distances = new ArrayList<>();

        refuels.forEach(refuel -> distances.add(refuel.getOdometer()));
        Collections.sort(distances);

        return distances.get(distances.size() - 1) - distances.get(0);
    }

    public BigDecimal calculateDistancePerDay(List<Refuels> refuels) {
        long totalDistance = calculateTotalDistance(refuels);

        return BigDecimal.valueOf(totalDistance).divide(calculateAmountOfDays(refuels), RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAmountOfDays(List<Refuels> refuels, List<Expense> expenses) {
        List<LocalDateTime> dateTimes = new ArrayList<>();

        refuels.forEach(refuel -> dateTimes.add(refuel.getDate()));
        expenses.forEach(expense -> dateTimes.add(expense.getDate()));

        Collections.sort(dateTimes);

        long duration = ChronoUnit.DAYS.between(dateTimes.get(0).toLocalDate(), dateTimes.get(dateTimes.size() - 1).toLocalDate());

        if (duration == 0) {
            return BigDecimal.ONE;
        }
        return BigDecimal.valueOf(duration);
    }

    public BigDecimal calculateAmountOfDays(List<Refuels> refuels) {
        List<LocalDateTime> dateTimes = new ArrayList<>();

        refuels.forEach(refuel -> dateTimes.add(refuel.getDate()));

        Collections.sort(dateTimes);

        long duration = ChronoUnit.DAYS.between(dateTimes.get(0).toLocalDate(), dateTimes.get(dateTimes.size() - 1).toLocalDate());
        return BigDecimal.valueOf(duration);
    }
}
