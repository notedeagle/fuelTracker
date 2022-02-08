package com.fuel.tracker.fueltracker.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Refuel extends BaseEntity {

    private LocalDateTime date;
    private long odometer;
    @Enumerated(EnumType.STRING)
    private Fuel fuel;
    private BigDecimal price;
    private BigDecimal totalCost;
    private BigDecimal litres;
    private boolean fullTank;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
