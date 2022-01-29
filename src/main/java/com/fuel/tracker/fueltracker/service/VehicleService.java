package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getAllUserVehicles() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = customerRepository.findByEmail(email).orElseThrow(IllegalStateException::new).getId();
        return vehicleRepository.findAllByCustomerId(userId).orElseThrow(IllegalStateException::new);
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepository.findByEmail(email).map(customer -> {
            vehicle.setCustomer(customer);
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
