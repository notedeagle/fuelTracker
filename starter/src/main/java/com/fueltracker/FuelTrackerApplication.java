package com.fueltracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.fuelTracker", "customers", "vehicles", "refuel", "expense", "totalcost"})
public class FuelTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FuelTrackerApplication.class, args);
    }
}
