package com.fueltracker.model.entity;

import com.fueltracker.model.dto.VehicleDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VEHICLES")
public class Vehicles {

    @Id
    @NotNull
    @Column(name = "VEH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "VEH_CREATED")
    private LocalDateTime created;

    @NotNull
    @LastModifiedDate
    @Column(name = "VEH_UPDATED")
    private LocalDateTime updated;

    @NotBlank(message = "Name must not be blank.")
    @Column(name = "VEH_NAME")
    private String name;

    @NotBlank(message = "Vehicle brand must not be blank.")
    @Column(name = "VEH_BRAND")
    private String brand;

    @NotBlank(message = "Model must not be blank.")
    @Column(name = "VEH_MODEL")
    private String model;

    @NotNull
    @Column(name = "VEH_YR_OF_PROD")
    private Integer yearOfProduction;

    @NotBlank(message = "Plate number must not be blank.")
    @Column(name = "VEH_PLATE_NUMBER")
    private String plateNumber;

    @NotNull(message = "Tank capacity must not be blank.")
    @Column(name = "VEH_CAPACITY")
    private BigDecimal capacity;

    @NotNull(message = "Vehicle type must not be blank.")
    @Enumerated(EnumType.STRING)
    @Column(name = "VEH_TYPE")
    private VehicleType type;

    @ManyToOne
    @JoinColumn(name = "VEH_CUS_ID")
    private Customers customer;

    @OneToMany(mappedBy = "VEHICLES", cascade = CascadeType.REMOVE)
    private Set<Refuels> refuel;

    @OneToMany(mappedBy = "VEHICLES", cascade = CascadeType.REMOVE)
    private Set<Expense> expense;

    public Vehicles(VehicleDto vehicleDto) {
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
