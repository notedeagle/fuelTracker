package com.fueltracker.controller;

import com.fueltracker.model.dto.AllCustomerCostDto;
import com.fueltracker.service.TotalCostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/totalCost")
@SecurityRequirement(name = "bearerAuth")
public class TotalCostController {

    private final TotalCostService totalCostService;

    @GetMapping("/{vehicleName}")
    public AllCustomerCostDto getTotalCost(@PathVariable String vehicleName) {
        return totalCostService.getTotalCost(vehicleName);
    }
}
