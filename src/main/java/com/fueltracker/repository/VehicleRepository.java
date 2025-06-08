package com.fueltracker.repository;

import com.fueltracker.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<List<Vehicle>> findAllByCustomerId(Long id);

    Optional<Vehicle> findVehicleByNameAndCustomerId(@Param("name") String name, @Param("id") Long id);

    @Override
    @NonNull
    @Query("from Vehicle v left join fetch v.refuels left join fetch v.expenses")
    List<Vehicle> findAll();
}
