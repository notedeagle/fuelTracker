package com.fuel.tracker.fueltracker.model.dto;

import com.fuel.tracker.fueltracker.model.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@Setter
public class VehicleDto {
    private String name;
    private String brand;
    private String model;
    private BigDecimal capacity;
    private int yearOfProduction;
    private String plateNumber;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
