package com.fuel.tracker.fueltracker.service;

import com.fuel.tracker.fueltracker.config.security.RegistrationCredentials;
import com.fuel.tracker.fueltracker.mode.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public void register(RegistrationCredentials request) {
        userService.singUpUser(new User(request.getUsername(), request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getPassword()));
    }
}
