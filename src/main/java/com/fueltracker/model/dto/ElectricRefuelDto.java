package com.fueltracker.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ElectricRefuelDto {
    private LocalDateTime date;
    private int odometer;
    private boolean fullTank;
    private double startLvl;
    private double endLvl;
    private BigDecimal price;
}
