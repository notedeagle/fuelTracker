package com.fueltracker.controller;

import com.fueltracker.model.dto.ElectricRefuelDto;
import com.fueltracker.model.dto.RefuelDto;
import com.fueltracker.model.entity.Fuel;
import com.fueltracker.model.entity.Refuels;
import com.fueltracker.model.entity.Vehicles;
import com.fueltracker.service.RefuelService;
import com.fueltracker.service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/refuel")
@SecurityRequirement(name = "bearerAuth")
public class RefuelController {

    private final RefuelService refuelService;
    private final VehicleService vehicleService;
    private final ModelMapper mapper;

    @GetMapping("/{vehicleName}")
    public List<RefuelDto> findAllRefuelByVehicleName(@PathVariable String vehicleName) {
        Vehicles vehicle = vehicleService.getCustomerVehicleByName(vehicleName);
        return refuelService.getAllCarRefuel(vehicle.getId()).stream()
                .map(v -> mapper.map(v, RefuelDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/{vehicleName}")
    public RefuelDto addRefuel(@RequestBody RefuelDto refuelDto, @PathVariable String vehicleName) {
        Refuels refuel = mapper.map(refuelDto, Refuels.class);
        return mapper.map(refuelService.addRefuel(refuel, vehicleName), RefuelDto.class);
    }

    @PostMapping("/electric/{vehicleName}")
    public RefuelDto addElectricRefuel(@RequestBody ElectricRefuelDto electricRefuelDto, @PathVariable String vehicleName) {
        Refuels refuel = Refuels.builder()
                .date(electricRefuelDto.getDate())
                .odometer(electricRefuelDto.getOdometer())
                .fuel(Fuel.ELECTRIC)
                .price(electricRefuelDto.getPrice())
                .fullTank(electricRefuelDto.isFullTank())
                .freeTank(false)
                .totalCost(refuelService.calculateTotalCost(electricRefuelDto.getPrice(),
                        electricRefuelDto.getStartLvl(), electricRefuelDto.getEndLvl(), vehicleName))
                .litres(refuelService.calculateCharging(electricRefuelDto.getStartLvl(), electricRefuelDto.getEndLvl(),
                        vehicleName))
                .build();

        return mapper.map(refuelService.addRefuel(refuel, vehicleName), RefuelDto.class);
    }

    @Transactional
    @DeleteMapping("/{refuelId}")
    public void deleteRefuel(@PathVariable long refuelId) {
        refuelService.deleteRefuel(refuelId);
    }
}
