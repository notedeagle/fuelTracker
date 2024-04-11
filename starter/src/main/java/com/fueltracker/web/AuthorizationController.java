package com.fueltracker.web;

import com.fueltracker.config.security.AuthenticationResponse;
import com.fueltracker.config.security.LoginCredentials;
import com.fueltracker.config.security.RegistrationCredentials;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistance.repository.CustomerRepository;


@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationCredentials request) {
        boolean userUsernameExist = customerRepository.findByUsername(request.getUsername()).isPresent();
        boolean userEmailExist = customerRepository.findByEmail(request.getEmail()).isPresent();

        if (userUsernameExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
        } else if (userEmailExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email address already taken.");
        } else {
            authorizationService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body("Account created.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginCredentials credentials) {
        return ResponseEntity.ok(authorizationService.authenticate(credentials));
    }

    @GetMapping("/secured")
    public String secured() {
        return "secured";
    }
}
