package web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import persistance.dto.ExpenseDto;
import persistance.entity.Expense;
import persistance.entity.Vehicle;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/expense")
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final VehicleService vehicleService;
    private final ModelMapper mapper;

    @GetMapping("/{vehicleName}")
    public List<ExpenseDto> findAllExpensesByVehicleName(@PathVariable String vehicleName) {
        Vehicle vehicle = vehicleService.getCustomerVehicleByName(vehicleName);

        return expenseService.getAllExpenses(vehicle.getId()).stream()
                .map(v -> mapper.map(v, ExpenseDto.class))
                .toList();
    }

    @PostMapping("/{vehicleName}")
    public ExpenseDto addExpense(@RequestBody ExpenseDto expenseDto, @PathVariable String vehicleName) {
        Expense expense = mapper.map(expenseDto, Expense.class);
        return mapper.map(expenseService.addExpense(expense, vehicleName), ExpenseDto.class);
    }

    @Transactional
    @DeleteMapping("/{expenseId}")
    public void deleteExpense(@PathVariable long expenseId) {
        expenseService.deleteExpense(expenseId);
    }
}
