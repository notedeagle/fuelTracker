package com.fueltracker.model.dto;

import com.fueltracker.model.entity.Fuel;
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
public class RefuelDto {
    private Long id;

    @NotNull(message = "Date must not be null")
    private LocalDateTime date;

    @NotNull(message = "Odometer reading must not be null")
    @PositiveOrZero(message = "Odometer reading must be positive or zero")
    private Integer odometer;

    @NotNull(message = "Fuel type must not be null")
    private Fuel fuel;

    @NotNull(message = "Price per unit must not be null")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Total cost must not be null")
    @PositiveOrZero(message = "Total cost must be positive or zero")
    private BigDecimal totalCost;

    @NotNull(message = "Volume must not be null")
    @Positive(message = "Volume must be greater than zero")
    private BigDecimal litres;

    @NotNull(message = "Full tank flag must not be null")
    private Boolean fullTank;

    @NotNull(message = "Free tank flag must not be null")
    private Boolean freeTank;

    // Opcjonalne położenie geograficzne - bez walidacji
    private Double latitude;
    private Double longitude;
}
