package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.repository.UserRepository;
import com.fuel.tracker.fueltracker.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getVehiclesByUserEmail(String email) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = userRepository.findByUsername(username).orElseThrow(IllegalStateException::new).getId();

        return vehicleRepository.findAllByUserId(userId).orElseThrow(IllegalStateException::new);
    }
}
