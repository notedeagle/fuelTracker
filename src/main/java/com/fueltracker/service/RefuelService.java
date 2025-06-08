package com.fueltracker.service;

import com.fueltracker.exception.ResourceNotFoundException;
import com.fueltracker.model.entity.Refuel;
import com.fueltracker.model.entity.Vehicle;
import com.fueltracker.repository.RefuelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefuelService {
    private static final int PERCENTAGE_DIVISOR = 100;

    private final RefuelRepository refuelRepository;
    private final VehicleService vehicleService;

    public List<Refuel> getAllCarRefuel(long vehicleId) {
        return refuelRepository.findAllByVehicleId(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("No refuels found for vehicle id: " + vehicleId));
    }

    @Transactional
    public Refuel addRefuel(Refuel refuel, String vehicleName) {
        Vehicle vehicle = vehicleService.getCustomerVehicleByName(vehicleName);
        refuel.setVehicle(vehicle);
        return refuelRepository.save(refuel);
    }

    @Transactional
    public void deleteRefuel(long id) {
        if (!refuelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Refuel with id " + id + " not found");
        }
        refuelRepository.deleteById(id);
    }

    public BigDecimal calculateTotalCost(BigDecimal price, double startLvl, double endLvl, String vehicleName) {
        return calculateCharging(startLvl, endLvl, vehicleName).multiply(price);
    }

    public BigDecimal calculateCharging(double startLvl, double endLvl, String vehicleName) {
        validateChargingLevels(startLvl, endLvl);

        BigDecimal capacity = vehicleService.getCustomerVehicleByName(vehicleName).getCapacity();
        BigDecimal chargingLvl = BigDecimal.valueOf(endLvl - startLvl);

        return capacity.multiply(chargingLvl.divide(BigDecimal.valueOf(PERCENTAGE_DIVISOR), RoundingMode.HALF_UP));
    }

    private void validateChargingLevels(double startLvl, double endLvl) {
        if (startLvl < 0 || startLvl > 100) {
            throw new IllegalArgumentException("Start level must be between 0 and 100");
        }
        if (endLvl < 0 || endLvl > 100) {
            throw new IllegalArgumentException("End level must be between 0 and 100");
        }
        if (startLvl >= endLvl) {
            throw new IllegalArgumentException("End level must be greater than start level");
        }
    }
}
