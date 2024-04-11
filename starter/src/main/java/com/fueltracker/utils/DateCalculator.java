package com.fueltracker.utils;


import persistance.dto.Dates;
import persistance.entity.Expense;
import persistance.entity.Refuel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DateCalculator {
    public Dates startAndEndDateCalculator(List<Refuel> refuels, List<Expense> expenses) {
        List<LocalDate> dates = new ArrayList<>();

        refuels.forEach(refuel -> dates.add(refuel.getDate().toLocalDate()));
        expenses.forEach(expense -> dates.add(expense.getDate().toLocalDate()));
        Collections.sort(dates);

        return Dates.builder()
                .startDate(dates.get(0))
                .endDate(dates.get(dates.size() - 1))
                .build();
    }

    public Dates startAndEndDateCalculator(List<Refuel> refuels) {
        List<LocalDate> dates = new ArrayList<>();

        refuels.forEach(refuel -> dates.add(refuel.getDate().toLocalDate()));
        Collections.sort(dates);

        return Dates.builder()
                .startDate(dates.get(0))
                .endDate(dates.get(dates.size() - 1))
                .build();
    }
}
