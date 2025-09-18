package com.fueltracker.utils;

import com.fueltracker.model.dto.Dates;
import com.fueltracker.model.entity.Expense;
import com.fueltracker.model.entity.Refuel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DateCalculator {

    public Dates startAndEndDateCalculator(List<Refuel> refuels, List<Expense> expenses) {
        List<LocalDate> dates = new ArrayList<>();

        refuels.forEach(refuel -> dates.add(refuel.getDate().toLocalDate()));
        expenses.forEach(expense -> dates.add(expense.getDate().toLocalDate()));
        Collections.sort(dates);

        return Dates.builder()
                .startDate(dates.getFirst())
                .endDate(dates.getLast())
                .build();
    }

    public Dates startAndEndDateCalculator(List<Refuel> refuels) {
        List<LocalDate> dates = new ArrayList<>();

        refuels.forEach(refuel -> dates.add(refuel.getDate().toLocalDate()));
        Collections.sort(dates);

        return Dates.builder()
                .startDate(dates.getFirst())
                .endDate(dates.getLast())
                .build();
    }
}
