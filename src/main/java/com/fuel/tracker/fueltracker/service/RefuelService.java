package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.model.entity.Refuel;
import com.fuel.tracker.fueltracker.repository.RefuelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RefuelService {

    private final RefuelRepository refuelRepository;
    private final VehicleService vehicleService;

    public List<Refuel> getAllCarRefuel(long carId) {
        return refuelRepository.findAllByVehicleId(carId).orElseThrow(IllegalStateException::new);
    }

    public Refuel addRefuel(Refuel refuel, String vehicleName) {
        refuel.setVehicle(vehicleService.getVehicleByName(vehicleName));
        return refuelRepository.save(refuel);
    }

    public void deleteRefuel(long id) {
        refuelRepository.deleteById(id);
    }
}
