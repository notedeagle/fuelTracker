package com.fueltracker.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CostPerMonth {
    private int monthNumber;
    private BigDecimal totalCost;
}
