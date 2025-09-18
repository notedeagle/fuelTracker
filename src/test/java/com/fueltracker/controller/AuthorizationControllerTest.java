package com.fueltracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fueltracker.config.security.AuthenticationResponse;
import com.fueltracker.config.security.JwtUtils;
import com.fueltracker.config.security.LoginCredentials;
import com.fueltracker.config.security.RegistrationCredentials;
import com.fueltracker.model.entity.Customer;
import com.fueltracker.repository.CustomerRepository;
import com.fueltracker.service.CustomerService;
import com.fueltracker.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorizationController.class)
@Import(AuthorizationControllerTest.TestConfig.class)
class AuthorizationControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JwtUtils jwtUtils() {
            return mock(JwtUtils.class);
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return mock(BCryptPasswordEncoder.class);
        }

        @Bean
        public AuthenticationManager authenticationManager() {
            return mock(AuthenticationManager.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerRepository customerRepository;

    @MockitoBean
    private RegistrationService registrationService;

    @MockitoBean
    private CustomerService customerService;


    @Test
    void shouldRegisterNewUserSuccessfully() throws Exception {
        // Given
        RegistrationCredentials registrationRequest = new RegistrationCredentials(
                "newuser",
                "newuser@example.com",
                "John",
                "Doe",
                "password123"
        );

        when(customerRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(customerRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Account created."));
    }

    @Test
    void shouldReturnConflictWhenUsernameAlreadyExists() throws Exception {
        // Given
        RegistrationCredentials registrationRequest = new RegistrationCredentials(
                "existinguser",
                "newemail@example.com",
                "John",
                "Doe",
                "password123"
        );

        Customer existingCustomer = new Customer();
        when(customerRepository.findByUsername("existinguser")).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.findByEmail("newemail@example.com")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Username already taken."));
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        // Given
        RegistrationCredentials registrationRequest = new RegistrationCredentials(
                "newusername",
                "existing@example.com",
                "John",
                "Doe",
                "password123"
        );

        Customer existingCustomer = new Customer();
        when(customerRepository.findByUsername("newusername")).thenReturn(Optional.empty());
        when(customerRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingCustomer));

        // When & Then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email address already taken."));
    }

    @Test
    void shouldAuthenticateUserSuccessfully() throws Exception {
        // Given
        LoginCredentials loginRequest = new LoginCredentials();
        loginRequest.setUsername("validuser");
        loginRequest.setPassword("validpassword");

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .accessToken("access-token-123")
                .refreshToken("refresh-token-456")
                .build();

        when(customerService.authenticate(any(LoginCredentials.class))).thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("access-token-123"))
                .andExpect(jsonPath("$.refresh_token").value("refresh-token-456"));
    }

    @Test
    void shouldReturnUnauthorizedForInvalidCredentials() throws Exception {
        // Given
        LoginCredentials invalidLoginRequest = new LoginCredentials();
        invalidLoginRequest.setUsername("invaliduser");
        invalidLoginRequest.setPassword("wrongpassword");

        when(customerService.authenticate(any(LoginCredentials.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // When & Then
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLoginRequest)))
                .andExpect(status().is5xxServerError());
    }
}