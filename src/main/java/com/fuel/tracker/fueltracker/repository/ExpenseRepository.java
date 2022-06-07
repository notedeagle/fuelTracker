package com.fuel.tracker.fueltracker.repository;

import com.fuel.tracker.fueltracker.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<List<Expense>> findAllByVehicleId(@Param("id") Long id);
}
