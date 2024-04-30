package expense;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import persistance.entity.Expense;
import persistance.repository.ExpenseRepository;
import vehicles.VehicleService;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final VehicleService vehicleService;

    @Cacheable(value = "expenses")
    public List<Expense> getAllExpenses(long vehicleId) {
        return expenseRepository.findAllByVehicleId(vehicleId).orElseThrow(IllegalStateException::new);
    }

    @Cacheable(value = "expenses")
    @CacheEvict(value = "expenses", allEntries = true)
    public Expense addExpense(Expense expense, String vehicleName) {
        expense.setVehicle(vehicleService.getCustomerVehicleByName(vehicleName));
        return expenseRepository.save(expense);
    }

    @CacheEvict(value = "expenses", allEntries = true)
    public void deleteExpense(long id) {
        expenseRepository.deleteById(id);
    }
}
