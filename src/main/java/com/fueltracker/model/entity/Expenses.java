package com.fueltracker.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EXPENSES")
public class Expenses {

    @Id
    @NotNull
    @Column(name = "EXP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "EXP_CREATED")
    private LocalDateTime created;

    @NotNull
    @LastModifiedDate
    @Column(name = "EXP_UPDATED")
    private LocalDateTime updated;

    @NotBlank(message = "Date must not be blank.")
    @Column(name = "EXP_DATE")
    private LocalDateTime date;

    @NotBlank(message = "Odometer must not be blank.")
    @Column(name = "EXP_ODOMETER")
    private Integer odometer;

    @NotBlank(message = "Total cost must not be blank.")
    @Column(name = "EXP_TOTAL_COST")
    private BigDecimal totalCost;

    @NotBlank
    @Column(name = "EXP_COMMENTS")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "EXP_VEH_ID")
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
