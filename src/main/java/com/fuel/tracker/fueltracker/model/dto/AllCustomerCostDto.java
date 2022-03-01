package com.fuel.tracker.fueltracker.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class AllCustomerCostDto {
    private BigDecimal totalCost;
    private BigDecimal costPerDay;
    private BigDecimal costPerKm;
    private long totalDistance;
    private BigDecimal distancePerDay;
    private Set<CostPerMonth> costPerMonthList;
}
