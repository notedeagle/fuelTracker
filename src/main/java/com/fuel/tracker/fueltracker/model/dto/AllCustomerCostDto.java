package com.fuel.tracker.fueltracker.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class AllCustomerCostDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalCost;
    private BigDecimal costPerDay;
    private BigDecimal costPerKm;
    private long totalDistance;
    private BigDecimal distancePerDay;
    private Set<CostPerMonth> costPerMonth;
}
