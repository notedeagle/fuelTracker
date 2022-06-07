package com.fuel.tracker.fueltracker.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class Expense extends BaseEntity {

    @NotBlank(message = "Date must not be blank.")
    private LocalDateTime date;
    @NotBlank(message = "Odometer must not be blank.")
    private long odometer;
    @NotBlank(message = "Total cost must not be blank.")
    private BigDecimal totalCost;
    private String note;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
