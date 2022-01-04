package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.model.dto.VehicleDto;
import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.service.VehicleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;
    private final ModelMapper mapper;

    @GetMapping
    public List<VehicleDto> findAllVehicle() {
        return vehicleService.getAllVehicles().stream()
                .map(e -> mapper.map(e, VehicleDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/user")
    public List<VehicleDto> findAllUserVehicle() {
        return vehicleService.getAllUserVehicles().stream()
                .map(e -> mapper.map(e, VehicleDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public VehicleDto addVehicle(@RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = mapper.map(vehicleDto, Vehicle.class);
        return mapper.map(vehicleService.addVehicle(vehicle), VehicleDto.class);
    }

    @DeleteMapping("/{vehicleName}")
    public void deleteVehicle(@PathVariable String vehicleName) {
        vehicleService.deleteVehicle(vehicleName);
    }
}
