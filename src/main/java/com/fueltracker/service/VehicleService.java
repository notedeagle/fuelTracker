package com.fueltracker.service;

import com.fueltracker.exception.ResourceNotFoundException;
import com.fueltracker.model.dto.VehicleDto;
import com.fueltracker.model.entity.Customer;
import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.repository.CustomerRepository;
import com.fueltracker.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class VehicleService {

    public static final String NOT_FOUND = " not found";
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDto.class))
                .toList();
    }

    public List<Vehicle> getAllUserVehicles() {
        String username = getCurrentUsername();
        long userId = customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with username " + username + NOT_FOUND))
                .getId();

        return vehicleRepository.findAllByCustomerId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No vehicles found for user " + username));
    }

    @Transactional
    public VehicleDto addVehicle(@Valid VehicleDto vehicleDto) {
        Objects.requireNonNull(vehicleDto, "Vehicle data cannot be null");

        String username = getCurrentUsername();
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with username " + username + NOT_FOUND));

        vehicleRepository.findVehicleByNameAndCustomerId(vehicleDto.getName(), customer.getId())
                .ifPresent(vehicle -> {
                    throw new IllegalArgumentException("Vehicle with name " + vehicleDto.getName() + " already exists");
                });

        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);
        vehicle.setCustomer(customer);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return modelMapper.map(savedVehicle, VehicleDto.class);
    }

    public Vehicle getCustomerVehicleByName(String vehicleName) {
        List<Vehicle> vehicles = getAllUserVehicles();

        return vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with name " + vehicleName + NOT_FOUND));
    }

    @Transactional
    public void deleteCustomerVehicleByName(String vehicleName) {
        List<Vehicle> vehicles = getAllUserVehicles();

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with name " + vehicleName + NOT_FOUND));

        vehicleRepository.deleteById(vehicle.getId());
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
