package com.fueltracker.web;

import com.fueltracker.config.security.AuthenticationResponse;
import com.fueltracker.config.security.LoginCredentials;
import com.fueltracker.config.security.RegistrationCredentials;
import com.fueltracker.config.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import persistance.entity.Customer;
import persistance.repository.CustomerRepository;

@Service
@AllArgsConstructor
public class AuthorizationService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public void register(RegistrationCredentials request) {
        singUpUser(new Customer(request.getUsername(), request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getPassword()));
    }

    public void singUpUser(Customer customer) {
        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customerRepository.save(customer);
    }

    public AuthenticationResponse authenticate(LoginCredentials loginCredentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword())
        );

        Customer customer = customerRepository.findByUsername(loginCredentials.getUsername()).orElseThrow();
        String token = jwtUtils.generateToken(customer);
        String refreshToken = jwtUtils.generateRefreshToken(customer);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }
}
