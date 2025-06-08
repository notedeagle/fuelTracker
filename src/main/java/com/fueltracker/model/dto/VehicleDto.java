package com.fueltracker.model.dto;

import com.fueltracker.model.entity.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Brand must not be blank")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @NotBlank(message = "Model must not be blank")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    private String model;

    @NotNull(message = "Tank capacity must not be null")
    @Positive(message = "Tank capacity must be greater than zero")
    private BigDecimal capacity;

    @NotNull(message = "Year of production must not be null")
    @Positive(message = "Year of production must be positive")
    private Integer yearOfProduction;

    @NotBlank(message = "Plate number must not be blank")
    @Size(min = 1, max = 20, message = "Plate number must be between 1 and 20 characters")
    private String plateNumber;

    @NotNull(message = "Vehicle type must not be null")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
