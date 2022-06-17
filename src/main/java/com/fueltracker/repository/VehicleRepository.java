package com.fueltracker.repository;

import com.fueltracker.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<List<Vehicle>> findAllByCustomerId(Long id);

    Optional<Vehicle> findVehicleByNameAndCustomerId(@Param("name") String name, @Param("id") Long id);

    @Override
    @Query("from Vehicle v left join fetch v.refuel left join fetch v.expense")
    List<Vehicle> findAll();
}
