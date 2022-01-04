package com.fuel.tracker.fueltracker.model.dto;

import com.fuel.tracker.fueltracker.model.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {
    private String name;
    private String brand;
    private String model;
    private int yearOfProduction;
    private String plateNumber;
    private long mileage;
    private VehicleType vehicleType;
    private int registrationYear;
}
