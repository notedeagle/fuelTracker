package com.fueltracker.controller;

import com.fueltracker.model.dto.VehicleDto;
import com.fueltracker.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vehicles")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Vehicle Controller", description = "API for managing vehicles")
public class VehicleController {
    private final VehicleService vehicleService;
    private final ModelMapper modelMapper;

    @GetMapping
    @Operation(summary = "Get all vehicles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all vehicles")
    })
    public ResponseEntity<List<VehicleDto>> findAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/user")
    @Operation(summary = "Get all vehicles for current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user's vehicles"),
        @ApiResponse(responseCode = "404", description = "User not found or has no vehicles")
    })
    public ResponseEntity<List<VehicleDto>> findAllUserVehicles() {
        List<VehicleDto> vehicles = vehicleService.getAllUserVehicles().stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vehicle created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Vehicle with this name already exists")
    })
    public ResponseEntity<VehicleDto> addVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        VehicleDto createdVehicle = vehicleService.addVehicle(vehicleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicle);
    }

    @DeleteMapping("/{vehicleName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a vehicle by name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehicle deleted"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<Void> deleteVehicle(@PathVariable String vehicleName) {
        vehicleService.deleteCustomerVehicleByName(vehicleName);
        return ResponseEntity.noContent().build();
    }
}
