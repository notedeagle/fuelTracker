package com.fueltracker.repository;

import com.fueltracker.model.entity.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicles, Long> {
    Optional<List<Vehicles>> findAllByCustomerId(Long id);

    Optional<Vehicles> findVehicleByNameAndCustomerId(@Param("name") String name, @Param("id") Long id);

    @Override
    @Query("from Vehicles v left join fetch v.refuel left join fetch v.expense")
    List<Vehicles> findAll();
}
