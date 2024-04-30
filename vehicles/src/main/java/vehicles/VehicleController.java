package vehicles;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import persistance.dto.VehicleDto;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/vehicle")
@SecurityRequirement(name = "bearerAuth")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<VehicleDto>> findAllVehicle() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/user")
    public List<VehicleDto> findAllCustomerVehicle() {
        return vehicleService.getAllUserVehicles().stream()
                .map(VehicleDto::new)
                .toList();
    }

    @PostMapping
    public VehicleDto addVehicle(@RequestBody VehicleDto vehicleDto) throws IllegalArgumentException {
        return vehicleService.addVehicle(vehicleDto);
    }

    @Transactional
    @DeleteMapping("/{vehicleName}")
    public void deleteVehicle(@PathVariable String vehicleName) throws IllegalArgumentException {
        vehicleService.deleteCustomerVehicleByName(vehicleName);
    }
}
