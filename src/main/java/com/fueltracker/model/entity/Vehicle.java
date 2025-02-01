package com.fueltracker.model.entity;

import com.fueltracker.model.dto.VehicleDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created;

    private LocalDateTime updated;

    @NotBlank(message = "Name must not be blank.")
    private String name;

    @NotBlank(message = "Vehicle brand must not be blank.")
    private String brand;

    @NotBlank(message = "Model must not be blank.")
    private String model;

    private int yearOfProduction;

    private String plateNumber;

    @NotNull(message = "Tank capacity must not be blank.")
    private BigDecimal capacity;

    @NotNull(message = "Vehicle type must not be blank.")
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE)
    private Set<Refuel> refuel;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE)
    private Set<Expense> expense;

    public Vehicle(VehicleDto vehicleDto) {
        this.name = vehicleDto.getName();
        this.brand = vehicleDto.getBrand();
        this.model = vehicleDto.getModel();
        this.yearOfProduction = vehicleDto.getYearOfProduction();
        this.plateNumber = vehicleDto.getPlateNumber();
        this.capacity = vehicleDto.getCapacity();
        this.type = vehicleDto.getVehicleType();
    }

    @PrePersist
    void prePersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        updated = LocalDateTime.now();
    }
}
