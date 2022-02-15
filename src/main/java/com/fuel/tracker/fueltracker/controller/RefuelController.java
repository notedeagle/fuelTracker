package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.model.dto.ElectricRefuelDto;
import com.fuel.tracker.fueltracker.model.dto.RefuelDto;
import com.fuel.tracker.fueltracker.model.entity.Fuel;
import com.fuel.tracker.fueltracker.model.entity.Refuel;
import com.fuel.tracker.fueltracker.model.entity.Vehicle;
import com.fuel.tracker.fueltracker.service.RefuelService;
import com.fuel.tracker.fueltracker.service.VehicleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/refuel")
public class RefuelController {

    private final RefuelService refuelService;
    private final VehicleService vehicleService;
    private final ModelMapper mapper;

    @GetMapping("/{vehicleName}")
    public List<RefuelDto> findAllRefuelByVehicleName(@PathVariable String vehicleName) {
        Vehicle vehicle = vehicleService.getVehicleByName(vehicleName);
        return refuelService.getAllCarRefuel(vehicle.getId()).stream()
                .map(v -> mapper.map(v, RefuelDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/{vehicleName}")
    public RefuelDto addRefuel(@RequestBody RefuelDto refuelDto, @PathVariable String vehicleName) {
        Refuel refuel = mapper.map(refuelDto, Refuel.class);
        return mapper.map(refuelService.addRefuel(refuel, vehicleName), RefuelDto.class);
    }

    @PostMapping("/electric/{vehicleName}")
    public RefuelDto addElectricRefuel(@RequestBody ElectricRefuelDto electricRefuelDto, @PathVariable String vehicleName) {
        Refuel refuel = Refuel.builder()
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

    @DeleteMapping("/{refuelId}")
    public void deleteRefuel(@PathVariable long refuelId) {
        refuelService.deleteRefuel(refuelId);
    }
}
