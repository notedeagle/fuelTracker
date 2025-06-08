package com.fueltracker.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "VEHICLES")
@EntityListeners(AuditingEntityListener.class)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VEH_ID")
    private Long id;

    @CreatedDate
    @Column(name = "VEH_CREATED", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "VEH_UPDATED", nullable = false)
    private LocalDateTime updated;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(name = "VEH_NAME", nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Vehicle brand must not be blank")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    @Column(name = "VEH_BRAND", nullable = false, length = 50)
    private String brand;

    @NotBlank(message = "Model must not be blank")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    @Column(name = "VEH_MODEL", nullable = false, length = 50)
    private String model;

    @NotNull(message = "Year of production must not be null")
    @Positive(message = "Year of production must be positive")
    @Column(name = "VEH_YEAR", nullable = false)
    private Integer yearOfProduction;

    @NotBlank(message = "Plate number must not be blank")
    @Size(min = 1, max = 20, message = "Plate number must be between 1 and 20 characters")
    @Column(name = "VEH_PLATE", nullable = false, length = 20)
    private String plateNumber;

    @NotNull(message = "Tank capacity must not be null")
    @Positive(message = "Tank capacity must be greater than zero")
    @Column(name = "VEH_CAPACITY", nullable = false, precision = 10, scale = 2)
    private BigDecimal capacity;

    @NotNull(message = "Vehicle type must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "VEH_TYPE", nullable = false)
    private VehicleType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEH_CUS_ID")
    private Customer customer;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE)
    private Set<Refuel> refuels;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE)
    private Set<Expense> expenses;
}
