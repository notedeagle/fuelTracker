package com.fueltracker.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectricRefuelDto {

    @NotNull(message = "Date must not be null")
    private LocalDateTime date;

    @NotNull(message = "Odometer reading must not be null")
    @PositiveOrZero(message = "Odometer reading must be positive or zero")
    private Integer odometer;

    @NotNull(message = "Full tank flag must not be null")
    private Boolean fullTank;

    @NotNull(message = "Start level must not be null")
    @PositiveOrZero(message = "Start level must be between 0 and 100")
    private Double startLvl;

    @NotNull(message = "End level must not be null")
    @Positive(message = "End level must be greater than zero and not more than 100")
    private Double endLvl;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;
}
