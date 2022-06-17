package com.fueltracker.controller;

import com.fueltracker.model.dto.VehicleDto;
import com.fueltracker.repository.CustomerRepository;
import com.fueltracker.repository.VehicleRepository;
import com.fueltracker.service.VehicleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .toList();
    }

    @PostMapping
    public VehicleDto addVehicle(@RequestBody VehicleDto vehicleDto) throws IllegalArgumentException {
        return vehicleService.addVehicle(vehicleDto);
    }

    @Transactional
    @DeleteMapping("/{vehicleName}")
    public void deleteVehicle(@PathVariable String vehicleName) {
        vehicleService.deleteCustomerVehicleByName(vehicleName);
    }
}
