package com.fueltracker.controller;

import com.fueltracker.model.dto.ElectricRefuelDto;
import com.fueltracker.model.dto.RefuelDto;
import com.fueltracker.model.entity.Fuel;
import com.fueltracker.model.entity.Refuel;
import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.service.RefuelService;
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

@RestController
@RequestMapping("/api/v1/refuels")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Refuel Controller", description = "API for managing vehicle refueling data")
@RequiredArgsConstructor
public class RefuelController {

    private final RefuelService refuelService;
    private final VehicleService vehicleService;
    private final ModelMapper mapper;

    @GetMapping("/{vehicleName}")
    @Operation(summary = "Get all refuels for a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved refuels"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<List<RefuelDto>> findAllRefuelByVehicleName(@PathVariable String vehicleName) {
        Vehicle vehicle = vehicleService.getCustomerVehicleByName(vehicleName);
        List<RefuelDto> refuels = refuelService.getAllCarRefuel(vehicle.getId()).stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(refuels);
    }

    @PostMapping("/{vehicleName}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a refuel record for a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Refuel record created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<RefuelDto> addRefuel(@Valid @RequestBody RefuelDto refuelDto, @PathVariable String vehicleName) {
        Refuel refuel = mapper.map(refuelDto, Refuel.class);
        Refuel savedRefuel = refuelService.addRefuel(refuel, vehicleName);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedRefuel));
    }

    @PostMapping("/electric/{vehicleName}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add an electric charging record for a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Electric refuel record created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<RefuelDto> addElectricRefuel(@Valid @RequestBody ElectricRefuelDto electricRefuelDto, @PathVariable String vehicleName) {
        Refuel refuel = createElectricRefuel(electricRefuelDto, vehicleName);
        Refuel savedRefuel = refuelService.addRefuel(refuel, vehicleName);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedRefuel));
    }

    @DeleteMapping("/{refuelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a refuel record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Refuel record deleted"),
        @ApiResponse(responseCode = "404", description = "Refuel record not found")
    })
    public ResponseEntity<Void> deleteRefuel(@PathVariable long refuelId) {
        refuelService.deleteRefuel(refuelId);
        return ResponseEntity.noContent().build();
    }

    private RefuelDto convertToDto(Refuel refuel) {
        return mapper.map(refuel, RefuelDto.class);
    }

    private Refuel createElectricRefuel(ElectricRefuelDto dto, String vehicleName) {
        return Refuel.builder()
                .date(dto.getDate())
                .odometer(dto.getOdometer())
                .fuel(Fuel.ELECTRIC)
                .price(dto.getPrice())
                .fullTank(dto.getFullTank())
                .freeTank(false)
                .totalCost(refuelService.calculateTotalCost(
                        dto.getPrice(),
                        dto.getStartLvl(),
                        dto.getEndLvl(),
                        vehicleName))
                .litres(refuelService.calculateCharging(
                        dto.getStartLvl(),
                        dto.getEndLvl(),
                        vehicleName))
                .build();
    }
}
