package com.fueltracker.service;

import com.fueltracker.config.security.RegistrationCredentials;
import com.fueltracker.model.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final CustomerService customerService;

    public void register(RegistrationCredentials request) {
        customerService.singUpUser(new Customer(request.getUsername(), request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getPassword()));
    }
}
