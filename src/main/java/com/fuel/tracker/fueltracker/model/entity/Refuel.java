package com.fuel.tracker.fueltracker.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Refuel extends BaseEntity {

    @NotBlank(message = "Date must not be blank.")
    private LocalDateTime date;
    @NotBlank(message = "Odometer must not be blank.")
    private long odometer;
    @NotBlank(message = "Fuel type must not be blank.")
    @Enumerated(EnumType.STRING)
    private Fuel fuel;
    @NotBlank(message = "Price must not be blank.")
    private BigDecimal price;
    @NotBlank(message = "Total cost must not be blank.")
    private BigDecimal totalCost;
    @NotBlank(message = "Liters must not be blank.")
    private BigDecimal litres;
    @NotBlank(message = "Full tank must not be blank.")
    private boolean fullTank;
    @NotBlank(message = "Free tank must not be blank.")
    private boolean freeTank;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
