package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.config.security.RegistrationCredentials;
import com.fuel.tracker.fueltracker.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public void register(@RequestBody RegistrationCredentials request) {
        registrationService.register(request);
    }
}
