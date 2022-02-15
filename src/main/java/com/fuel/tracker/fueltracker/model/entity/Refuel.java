package com.fuel.tracker.fueltracker.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Refuel extends BaseEntity {

    private LocalDateTime date;
    private long odometer;
    @Enumerated(EnumType.STRING)
    private Fuel fuel;
    private BigDecimal price;
    private BigDecimal totalCost;
    private BigDecimal litres;
    private boolean fullTank;
    private boolean freeTank;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
