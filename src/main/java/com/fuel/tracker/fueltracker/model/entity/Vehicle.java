package com.fuel.tracker.fueltracker.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String brand;
    private String model;
    private int yearOfProduction;
    private String plateNumber;
    private long mileage;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private int registrationYear;
    @ManyToOne
    private Customer user;
}
