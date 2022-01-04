package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> findAllVehicle() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{userEmail}")
    public List<Vehicle> findAllUserVehicle(@PathVariable String userEmail) {
        return vehicleService.getVehiclesByUserEmail(userEmail);
    }

    @PostMapping
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(vehicle);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}
