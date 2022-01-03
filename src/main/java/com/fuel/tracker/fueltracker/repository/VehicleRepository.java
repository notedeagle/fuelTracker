package com.fuel.tracker.fueltracker.repository;

import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<List<Vehicle>> findAllByUserId(long id);
}
