package com.fuel.tracker.fueltracker.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Dates {
    private LocalDate startDate;
    private LocalDate endDate;
}
