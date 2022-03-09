package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.model.dto.VehicleDto;
import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.repository.CustomerRepository;
import com.fuel.tracker.fueltracker.repository.VehicleRepository;
import com.fuel.tracker.fueltracker.service.VehicleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<VehicleDto>> findAllVehicle() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/user")
    public List<VehicleDto> findAllCustomerVehicle() {
        return vehicleService.getAllUserVehicles().stream()
                .map(e -> mapper.map(e, VehicleDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> addVehicle(@RequestBody VehicleDto vehicleDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Vehicle vehicle = mapper.map(vehicleDto, Vehicle.class);
        boolean vehicleExists = vehicleRepository.findVehicleByNameAndCustomerId(vehicleDto.getName(),
                customerRepository.findByEmail(email).orElseThrow().getId()).isPresent();

        if (vehicleExists) {
            return ResponseEntity.notFound().build();
        }

        vehicleService.addVehicle(vehicle);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{vehicleName}")
    public void deleteVehicle(@PathVariable String vehicleName) {
        vehicleService.deleteVehicle(vehicleName);
    }
}
