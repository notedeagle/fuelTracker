package com.fueltracker.utils;

import com.fueltracker.model.dto.Dates;
import com.fueltracker.model.entity.Expense;
import com.fueltracker.model.entity.Refuels;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DateCalculator {
    public Dates startAndEndDateCalculator(List<Refuels> refuels, List<Expense> expenses) {
        List<LocalDate> dates = new ArrayList<>();

        refuels.forEach(refuel -> dates.add(refuel.getDate().toLocalDate()));
        expenses.forEach(expense -> dates.add(expense.getDate().toLocalDate()));
        Collections.sort(dates);

        return Dates.builder()
                .startDate(dates.get(0))
                .endDate(dates.get(dates.size() - 1))
                .build();
    }

    public Dates startAndEndDateCalculator(List<Refuels> refuels) {
        List<LocalDate> dates = new ArrayList<>();

        refuels.forEach(refuel -> dates.add(refuel.getDate().toLocalDate()));
        Collections.sort(dates);

        return Dates.builder()
                .startDate(dates.get(0))
                .endDate(dates.get(dates.size() - 1))
                .build();
    }
}
