package com.fueltracker.repository;

import com.fueltracker.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(@Param("userName") String userName);
    Optional<Customer> findByEmail(@Param("email") String email);

    @Override
    @Query("from Customer c left join fetch c.vehicles")
    List<Customer> findAll();
}
