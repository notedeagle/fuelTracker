package com.fueltracker.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseDto {
    private long id;
    private LocalDateTime date;
    private long odometer;
    private BigDecimal totalCost;
    private String note;
}
