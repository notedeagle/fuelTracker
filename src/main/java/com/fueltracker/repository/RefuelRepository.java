package com.fueltracker.repository;

import com.fueltracker.model.entity.Refuels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefuelRepository extends JpaRepository<Refuels, Long> {
    Optional<List<Refuels>> findAllByVehicleId(@Param("id") Long id);
}
