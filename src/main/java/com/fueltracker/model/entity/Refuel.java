package com.fueltracker.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Refuel extends BaseEntity {

    @NotNull(message = "Date must not be blank.")
    private LocalDateTime date;
    @NotNull(message = "Odometer must not be blank.")
    private Long odometer;
    @NotNull(message = "Fuel type must not be blank.")
    @Enumerated(EnumType.STRING)
    private Fuel fuel;
    @NotNull(message = "Price must not be blank.")
    private BigDecimal price;
    @NotNull(message = "Total cost must not be blank.")
    private BigDecimal totalCost;
    @NotNull(message = "Liters must not be blank.")
    private BigDecimal litres;
    @NotNull(message = "Full tank must not be blank.")
    private Boolean fullTank;
    @NotNull(message = "Free tank must not be blank.")
    private Boolean freeTank;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
