package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.dto.VehicleDto;
import com.fuel.tracker.fueltracker.model.entity.Customer;
import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.repository.CustomerRepository;
import com.fuel.tracker.fueltracker.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record VehicleService(VehicleRepository vehicleRepository,
                             CustomerRepository customerRepository,
                             ModelMapper mapper) {

    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(vehicle -> mapper.map(vehicle, VehicleDto.class))
                .toList();
    }

    public List<Vehicle> getAllUserVehicles() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer with given email not found."))
                .getId();

        return vehicleRepository.findAllByCustomerId(userId).orElseThrow(IllegalStateException::new);
    }

    public VehicleDto addVehicle(Vehicle source) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer with given id not found."));

        source.setCustomer(customer);
        Vehicle vehicle = vehicleRepository.save(source);

        return new VehicleDto(vehicle);
    }

    public Vehicle getCustomerVehicleByName(String vehicleName) {
        List<Vehicle> vehicles = getAllUserVehicles();

        return vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny().orElseThrow(IllegalStateException::new);
    }

    public void deleteCustomerVehicleByName(String vehicleName) {
        List<Vehicle> vehicles = getAllUserVehicles();

        Optional<Vehicle> vehicle = vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny();

        vehicleRepository.deleteById(vehicle.orElseThrow().getId());
    }
}
