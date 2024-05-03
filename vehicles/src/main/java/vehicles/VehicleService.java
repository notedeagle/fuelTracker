package vehicles;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import persistance.dto.VehicleDto;
import persistance.entity.Customer;
import persistance.entity.Vehicle;
import persistance.repository.CustomerRepository;
import persistance.repository.VehicleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @Cacheable(value = "vehicles")
    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(VehicleDto::new)
                .toList();
    }

    public List<Vehicle> getAllUserVehicles() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = customerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer with given username not found"))
                .getId();

        return vehicleRepository.findAllByCustomerId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicles not found"));
    }

    @Cacheable(value = "vehicles")
    public List<VehicleDto> getAllUserVehiclesDto() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = customerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer with given username not found"))
                .getId();

        return vehicleRepository.findAllByCustomerId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicles not found"))
                .stream()
                .map(VehicleDto::new)
                .toList();
    }

    @CachePut(value = "vehicles")
    @CacheEvict(value = "vehicles", allEntries = true)
    public VehicleDto addVehicle(VehicleDto source) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Customer with given id not found."));

        vehicleRepository.findVehicleByNameAndCustomerId(source.getName(), customer.getId())
                .ifPresent(vehicle -> {
                    throw new IllegalArgumentException(source.getName());
                });


        Vehicle vehicle = new Vehicle(source);
        vehicle.setCustomer(customer);
        vehicleRepository.save(vehicle);

        return source;
    }

    public Vehicle getCustomerVehicleByName(String vehicleName) {
        List<Vehicle> vehicles = getAllUserVehicles();

        return vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with given name not found"));
    }

    @CacheEvict(value = "vehicles", allEntries = true)
    public void deleteCustomerVehicleByName(String vehicleName) {
        List<Vehicle> vehicles = getAllUserVehicles();

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getName().equals(vehicleName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with given name not found"));

        vehicleRepository.deleteById(vehicle.getId());
    }
}
