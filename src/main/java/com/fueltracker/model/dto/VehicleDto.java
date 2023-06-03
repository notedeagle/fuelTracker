package com.fueltracker.model.dto;

import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.model.entity.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
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
