package com.fueltracker.utils;

import com.fueltracker.model.entity.Expense;
import com.fueltracker.model.entity.Refuel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DistanceCalculator {

    public int calculateTotalDistance(List<Refuel> refuels, List<Expense> expenses) {
        List<Integer> distances = new ArrayList<>();

        refuels.forEach(refuel -> distances.add(refuel.getOdometer()));
        expenses.forEach(expense -> distances.add(expense.getOdometer()));
        Collections.sort(distances);

        return distances.getLast() - distances.getFirst();
    }

    public BigDecimal calculateDistancePerDay(List<Refuel> refuels, List<Expense> expenses) {
        int totalDistance = calculateTotalDistance(refuels, expenses);

        return BigDecimal.valueOf(totalDistance).divide(calculateAmountOfDays(refuels, expenses), RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAmountOfDays(List<Refuel> refuels, List<Expense> expenses) {
        List<LocalDateTime> dateTimes = new ArrayList<>();

        refuels.forEach(refuel -> dateTimes.add(refuel.getDate()));
        expenses.forEach(expense -> dateTimes.add(expense.getDate()));

        Collections.sort(dateTimes);

        long duration = ChronoUnit.DAYS.between(dateTimes.getFirst().toLocalDate(), dateTimes.getLast().toLocalDate());

        if (duration == 0) {
            return BigDecimal.ONE;
        }
        return BigDecimal.valueOf(duration);
    }
}
