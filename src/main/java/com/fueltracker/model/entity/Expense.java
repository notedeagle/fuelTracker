package com.fueltracker.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "EXPENSES")
@EntityListeners(AuditingEntityListener.class)
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXP_ID")
    private Long id;

    @CreatedDate
    @Column(name = "EXP_CREATED", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "EXP_UPDATED", nullable = false)
    private LocalDateTime updated;

    @NotNull(message = "Date must not be null")
    @Column(name = "EXP_DATE", nullable = false)
    private LocalDateTime date;

    @NotNull(message = "Odometer reading must not be null")
    @PositiveOrZero(message = "Odometer reading must be positive or zero")
    @Column(name = "EXP_ODOMETER", nullable = false)
    private Integer odometer;

    @NotNull(message = "Total cost must not be null")
    @Positive(message = "Total cost must be greater than zero")
    @Column(name = "EXP_TOTAL_COST", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;

    @NotBlank(message = "Comment must not be blank")
    @Column(name = "EXP_COMMENTS", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXP_VEH_ID")
    private Vehicle vehicle;
}
