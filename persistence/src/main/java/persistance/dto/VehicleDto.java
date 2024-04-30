package persistance.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import persistance.entity.Vehicle;
import persistance.entity.VehicleType;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class VehicleDto implements Serializable {
    private String name;
    private String brand;
    private String model;
    private BigDecimal capacity;
    private int yearOfProduction;
    private String plateNumber;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    public VehicleDto(Vehicle vehicle) {
        this.name = vehicle.getName();
        this.brand = vehicle.getBrand();
        this.model = vehicle.getModel();
        this.capacity = vehicle.getCapacity();
        this.yearOfProduction = vehicle.getYearOfProduction();
        this.plateNumber = vehicle.getPlateNumber();
        this.vehicleType = vehicle.getType();
    }
}
