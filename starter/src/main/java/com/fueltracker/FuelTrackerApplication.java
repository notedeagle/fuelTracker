package com.fueltracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.CustomerService;

@SpringBootApplication(scanBasePackageClasses = {CustomerService.class}, scanBasePackages = "com.fuelTracker")
public class FuelTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FuelTrackerApplication.class, args);
    }
}
