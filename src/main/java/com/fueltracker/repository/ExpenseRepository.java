package com.fueltracker.repository;

import com.fueltracker.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<List<Expense>> findAllByVehicleId(@Param("id") Long id);
}
