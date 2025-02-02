package com.fueltracker.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REFUELS")
public class Refuels {

    @Id
    @NotNull
    @Column(name = "REF_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "REF_CREATED")
    private LocalDateTime created;

    @NotNull
    @LastModifiedDate
    @Column(name = "REF_UPDATED")
    private LocalDateTime updated;

    @NotNull(message = "Date must not be blank.")
    @Column(name = "REF_DATE")
    private LocalDateTime date;

    @NotNull(message = "Odometer must not be blank.")
    @Column(name = "REF_ODOMETER")
    private Integer odometer;

    @NotNull(message = "Fuel type must not be blank.")
    @Enumerated(EnumType.STRING)
    @Column(name = "REF_FUEL")
    private Fuel fuel;

    @NotNull(message = "Price must not be blank.")
    @Column(name = "REF_PRICE")
    private BigDecimal price;

    @NotNull(message = "Total cost must not be blank.")
    @Column(name = "REF_TOTL_COST")
    private BigDecimal totalCost;

    @NotNull(message = "Liters must not be blank.")
    @Column(name = "REF_LITRES")
    private BigDecimal litres;

    @NotNull(message = "Full tank must not be blank.")
    @Column(name = "REF_FULL_TANK")
    private Boolean fullTank;

    @NotNull(message = "Free tank must not be blank.")
    @Column(name = "REF_FREE_TANK")
    private Boolean freeTank;

    @ManyToOne
    @JoinColumn(name = "REF_VEH_ID")
    private Vehicles vehicle;

    @PrePersist
    void prePersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        updated = LocalDateTime.now();
    }
}
