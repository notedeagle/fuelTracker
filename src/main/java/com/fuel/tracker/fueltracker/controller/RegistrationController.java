package com.fuel.tracker.fueltracker.controller;

import com.fuel.tracker.fueltracker.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
}
