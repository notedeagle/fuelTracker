package com.fueltracker.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REFUELS")
@EntityListeners(AuditingEntityListener.class)
public class Refuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REF_ID")
    private Long id;

    @CreatedDate
    @Column(name = "REF_CREATED", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "REF_UPDATED", nullable = false)
    private LocalDateTime updated;

    @NotNull(message = "Date must not be null")
    @Column(name = "REF_DATE", nullable = false)
    private LocalDateTime date;

    @NotNull(message = "Odometer reading must not be null")
    @PositiveOrZero(message = "Odometer reading must be positive or zero")
    @Column(name = "REF_ODOMETER", nullable = false)
    private Integer odometer;

    @NotNull(message = "Fuel type must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "REF_FUEL_TYPE", nullable = false)
    private Fuel fuel;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than zero")
    @Column(name = "REF_UNIT_PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Total cost must not be null")
    @PositiveOrZero(message = "Total cost must be positive or zero")
    @Column(name = "REF_TOTAL_COST", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;

    @NotNull(message = "Volume must not be null")
    @Positive(message = "Volume must be greater than zero")
    @Column(name = "REF_VOLUME", nullable = false, precision = 10, scale = 2)
    private BigDecimal litres;

    @NotNull(message = "Full tank flag must not be null")
    @Column(name = "REF_FULL_TANK", nullable = false)
    private Boolean fullTank;

    @NotNull(message = "Free tank flag must not be null")
    @Column(name = "REF_FREE_TANK", nullable = false)
    private Boolean freeTank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REF_VEH_ID")
    private Vehicle vehicle;
}
