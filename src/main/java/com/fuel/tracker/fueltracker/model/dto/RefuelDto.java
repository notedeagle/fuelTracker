package com.fuel.tracker.fueltracker.model.dto;

import com.fuel.tracker.fueltracker.model.entity.Fuel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RefuelDto {
    private long id;
    private LocalDateTime date;
    private long odometer;
    private Fuel fuel;
    private BigDecimal price;
    private BigDecimal totalCost;
    private BigDecimal litres;
    private BigDecimal avg;
    private boolean fullTank;
    private boolean freeTank;
}
