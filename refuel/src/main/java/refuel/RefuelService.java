package refuel;

import org.springframework.stereotype.Service;
import persistance.entity.Refuel;
import persistance.repository.RefuelRepository;
import vehicles.VehicleService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public record RefuelService(RefuelRepository refuelRepository, VehicleService vehicleService) {

    public List<Refuel> getAllCarRefuel(long vehicleId) {
        return refuelRepository.findAllByVehicleId(vehicleId).orElseThrow(IllegalStateException::new);
    }

    public Refuel addRefuel(Refuel refuel, String vehicleName) {
        refuel.setVehicle(vehicleService.getCustomerVehicleByName(vehicleName));
        return refuelRepository.save(refuel);
    }

    public void deleteRefuel(long id) {
        refuelRepository.deleteById(id);
    }

    public BigDecimal calculateTotalCost(BigDecimal price, double startLvl, double endLvl, String vehicleName) {
        return calculateCharging(startLvl, endLvl, vehicleName).multiply(price);
    }

    public BigDecimal calculateCharging(double startLvl, double endLvl, String vehicleName) {
        BigDecimal capacity = vehicleService.getCustomerVehicleByName(vehicleName).getCapacity();
        BigDecimal chargingLvl = BigDecimal.valueOf(endLvl - startLvl);

        return capacity.multiply(chargingLvl.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
    }
}
