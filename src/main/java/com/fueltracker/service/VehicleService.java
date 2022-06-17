package com.fueltracker.service;

import com.fueltracker.model.dto.VehicleDto;
import com.fueltracker.model.entity.Customer;
import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.repository.CustomerRepository;
import com.fueltracker.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public VehicleDto addVehicle(VehicleDto source) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer with given id not found."));

        boolean vehicleExists = vehicleRepository.findVehicleByNameAndCustomerId(source.getName(),
                customerRepository.findByEmail(email).orElseThrow().getId()).isPresent();

        if (vehicleExists) {
            throw new IllegalArgumentException(source.getName());
        }

        Vehicle vehicle = new Vehicle(source);
        vehicle.setCustomer(customer);
        vehicleRepository.save(vehicle);

        return source;
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
