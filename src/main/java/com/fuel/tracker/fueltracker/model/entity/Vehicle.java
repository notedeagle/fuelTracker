package com.fuel.tracker.fueltracker.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle extends BaseEntity {
    @NotBlank(message = "Name must not be blank.")
    private String name;
    @NotBlank(message = "Vehicle brand must not be blank.")
    private String brand;
    @NotBlank(message = "Model must not be blank.")
    private String model;
    private int yearOfProduction;
    private String plateNumber;
    @NotBlank(message = "Tank capacity must not be blank.")
    private BigDecimal capacity;
    @NotBlank(message = "Vehicle type must not be blank.")
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE)
    private Set<Refuel> refuel;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE)
    private Set<Expense> expense;
}
