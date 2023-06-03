package com.fueltracker.model.dto;

import com.fueltracker.model.entity.Fuel;
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
    private boolean fullTank;
    private boolean freeTank;
    private double latitude;
    private double longitude;
}
