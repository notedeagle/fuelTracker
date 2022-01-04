package com.fuel.tracker.fueltracker.repository;

import com.fuel.tracker.fueltracker.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String userName);
    Optional<Customer> findByEmail(String email);
}
