package com.fueltracker.model.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ExpenseDto {
    private Long id;

    @NotNull(message = "Date must not be null")
    private LocalDateTime date;

    @NotNull(message = "Odometer reading must not be null")
    @PositiveOrZero(message = "Odometer reading must be positive or zero")
    private Integer odometer;

    @NotNull(message = "Total cost must not be null")
    @Positive(message = "Total cost must be greater than zero")
    private BigDecimal totalCost;

    @NotBlank(message = "Comment must not be blank")
    private String comment;
}
