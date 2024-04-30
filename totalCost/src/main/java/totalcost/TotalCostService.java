package totalcost;

import expense.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import persistance.dto.AllCustomerCostDto;
import persistance.dto.Dates;
import persistance.entity.Expense;
import persistance.entity.Refuel;
import persistance.entity.Vehicle;
import refuel.RefuelService;
import totalcost.utils.CostCalculator;
import totalcost.utils.DateCalculator;
import totalcost.utils.DistanceCalculator;
import vehicles.VehicleService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TotalCostService {
    private final CostCalculator costCalculator;
    private final DistanceCalculator distanceCalculator;
    private final VehicleService vehicleService;
    private final RefuelService refuelService;
    private final ExpenseService expenseService;
    private final DateCalculator dateCalculator;


    @Cacheable(value = "totalCosts")
    public AllCustomerCostDto getTotalCost(String vehicleName) {
        Vehicle vehicle = vehicleService.getCustomerVehicleByName(vehicleName);

        List<Refuel> refuels = new ArrayList<>(refuelService.getAllCarRefuel(vehicle.getId()));
        List<Expense> expenses = new ArrayList<>(expenseService.getAllExpenses(vehicle.getId()));
        Dates dates = dateCalculator.startAndEndDateCalculator(refuels, expenses);

        return AllCustomerCostDto.builder()
                .startDate(dates.getStartDate())
                .endDate(dates.getEndDate())
                .totalCost(costCalculator.calculateTotalCost(refuels, expenses))
                .costPerDay(costCalculator.calculateTotalCostPerDay(refuels, expenses))
                .costPerKm(costCalculator.calculateTotalCostPerKm(refuels, expenses))
                .costPerMonth(costCalculator.getCostPerMonth(refuels, expenses))
                .totalDistance(distanceCalculator.calculateTotalDistance(refuels, expenses))
                .distancePerDay(distanceCalculator.calculateDistancePerDay(refuels, expenses))
                .build();
    }
}
