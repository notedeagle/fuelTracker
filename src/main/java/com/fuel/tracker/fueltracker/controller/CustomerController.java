package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.model.dto.CustomerUpdateDto;
import com.fuel.tracker.fueltracker.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PutMapping("/customer/{id}")
    ResponseEntity<?> updateCustomer(@PathVariable long id, @RequestBody @Valid CustomerUpdateDto customer) {
        return customerService.editCustomer(id, customer);
    }
}
