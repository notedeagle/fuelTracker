package com.fuel.tracker.fueltracker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle extends BaseEntity {

    private String name;
    private String brand;
    private String model;
    private int yearOfProduction;
    private String plateNumber;
    private long mileage;
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "vehicle")
    private Set<Refuel> refuel;
}
