package com.fueltracker.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Refuel extends BaseEntity implements Serializable {

    @Serial
    @Transient
    private static final long serialVersionUID = 1264084105211366869L;

    @NotNull(message = "Date must not be blank.")
    private LocalDateTime date;

    @NotNull(message = "Odometer must not be blank.")
    private Long odometer;

    @NotNull(message = "Fuel type must not be blank.")
    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @NotNull(message = "Price must not be blank.")
    private BigDecimal price;

    @NotNull(message = "Total cost must not be blank.")
    private BigDecimal totalCost;

    @NotNull(message = "Liters must not be blank.")
    private BigDecimal litres;

    @NotNull(message = "Full tank must not be blank.")
    private Boolean fullTank;

    @NotNull(message = "Free tank must not be blank.")
    private Boolean freeTank;

    @NotNull(message = "Latitude must not be null")
    private Double latitude;

    @NotNull(message = "Longitude must not be null")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
