package com.fueltracker.repository;

import com.fueltracker.model.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {
    Optional<Customers> findByUsername(@Param("userName") String userName);
    Optional<Customers> findByEmail(@Param("email") String email);

    @Override
    @Query("from Customers c left join fetch c.vehicles")
    List<Customers> findAll();
}
