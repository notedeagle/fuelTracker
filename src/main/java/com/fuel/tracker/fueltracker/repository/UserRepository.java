package com.fuel.tracker.fueltracker.repository;

import com.fuel.tracker.fueltracker.mode.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
    Optional<User> findByEmail(String email);
}
