package com.fueltracker.repository;

import com.fueltracker.model.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expenses, Long> {
    Optional<List<Expenses>> findAllByVehicleId(@Param("id") Long id);
}
