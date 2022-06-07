package com.fuel.tracker.fueltracker.repository;

import com.fuel.tracker.fueltracker.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(@Param("userName") String userName);
    Optional<Customer> findByEmail(@Param("email") String email);
}
