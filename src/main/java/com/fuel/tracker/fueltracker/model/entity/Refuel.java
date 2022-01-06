package com.fuel.tracker.fueltracker.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Refuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime date;
    private long odometer;
    @Enumerated(EnumType.STRING)
    private Fuel fuel;
    private BigDecimal price;
    private BigDecimal totalCost;
    private BigDecimal litres;
    private boolean fullTank;
    @ManyToOne
    private Vehicle vehicle;
}
