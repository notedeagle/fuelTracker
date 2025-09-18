package com.fueltracker.config.security;

import lombok.Data;


@Data
public class LoginCredentials {
    private String username;
    private String password;
}
