package com.fueltracker.service;

import com.fueltracker.model.dto.VehicleDto;
import com.fueltracker.model.entity.Customers;
import com.fueltracker.model.entity.Vehicles;
import com.fueltracker.repository.CustomerRepository;
import com.fueltracker.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record VehicleService(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {

    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(VehicleDto::new)
                .toList();
    }

    public List<Vehicles> getAllUserVehicles() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = customerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer with given username not found"))
                .getId();

        return vehicleRepository.findAllByCustomerId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicles not found"));
    }

    public VehicleDto addVehicle(VehicleDto source) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customers customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Customer with given id not found."));

        vehicleRepository.findVehicleByNameAndCustomerId(source.getName(), customer.getId())
                .ifPresent(vehicle -> {
                    throw new IllegalArgumentException(source.getName());
                });


        Vehicles vehicle = new Vehicles(source);
        vehicle.setCustomer(customer);
        vehicleRepository.save(vehicle);

        return source;
    }

    public Vehicles getCustomerVehicleByName(String vehicleName) {
        List<Vehicles> vehicles = getAllUserVehicles();

        return vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with given name not found"));
    }

    public void deleteCustomerVehicleByName(String vehicleName) {
        List<Vehicles> vehicles = getAllUserVehicles();

        Vehicles vehicle = vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with given name not found"));

        vehicleRepository.deleteById(vehicle.getId());
    }
}
