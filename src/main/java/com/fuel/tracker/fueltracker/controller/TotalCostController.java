package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.model.dto.AllCustomerCostDto;
import com.fuel.tracker.fueltracker.service.TotalCostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/totalCost")
public class TotalCostController {

    private final TotalCostService totalCostService;

    @GetMapping
    public AllCustomerCostDto getTotalCost() {
        return totalCostService.getTotalCost();
    }
}
