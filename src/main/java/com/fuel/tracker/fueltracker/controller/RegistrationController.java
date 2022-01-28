package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.config.security.RegistrationCredentials;
import com.fuel.tracker.fueltracker.repository.CustomerRepository;
import com.fuel.tracker.fueltracker.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationCredentials request) {
        boolean userUsernameExist = customerRepository.findByUsername(request.getUsername()).isPresent();
        boolean userEmailExist = customerRepository.findByEmail(request.getEmail()).isPresent();

        if (userUsernameExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
        } else if (userEmailExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email address already taken.");
        } else {
            registrationService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body("Account created.");
        }
    }
}
