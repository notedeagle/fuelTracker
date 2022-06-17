package com.fuel.tracker.fueltracker.model.dto;

import com.fuel.tracker.fueltracker.model.entity.Vehicle;
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

    public VehicleDto(Vehicle vehicle) {
        this.name = vehicle.getName();
        this.brand = vehicle.getBrand();
        this.model = vehicle.getModel();
        this.capacity = vehicle.getCapacity();
        this.yearOfProduction = vehicle.getYearOfProduction();
        this.plateNumber = vehicle.getPlateNumber();
        this.vehicleType = vehicle.getType();
    }
}
