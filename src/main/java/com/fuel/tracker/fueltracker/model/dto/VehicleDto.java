package com.fuel.tracker.fueltracker.model.dto;

import com.fuel.tracker.fueltracker.model.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class VehicleDto {
    private String name;
    private String brand;
    private String model;
    private int yearOfProduction;
    private String plateNumber;
    private long mileage;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
