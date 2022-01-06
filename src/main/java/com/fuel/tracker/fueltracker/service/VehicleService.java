package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.repository.UserRepository;
import com.fuel.tracker.fueltracker.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getAllUserVehicles() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = userRepository.findByUsername(username).orElseThrow(IllegalStateException::new).getId();
        return vehicleRepository.findAllByUserId(userId).orElseThrow(IllegalStateException::new);
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).map(user -> {
            vehicle.setUser(user);
            return vehicleRepository.save(vehicle);
        }).orElseThrow(IllegalStateException::new);
    }

    public Vehicle getVehicleByName(String vehicleName) {
        List<Vehicle> vehicles = getAllUserVehicles();

        return vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny().orElseThrow(IllegalStateException::new);
    }

    public void deleteVehicle(String vehicleName) {
        List<Vehicle> vehicles = getAllUserVehicles();

        Optional<Vehicle> vehicle = vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny();

        vehicleRepository.deleteById(vehicle.orElseThrow().getId());
    }
}
