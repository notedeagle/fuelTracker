package com.fueltracker.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Expense extends BaseEntity implements Serializable {

    @Serial
    @Transient
    private static final long serialVersionUID = 2985438157612673779L;

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
