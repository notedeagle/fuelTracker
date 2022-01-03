package com.fuel.tracker.fueltracker.config.security;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationCredentials {
    private final String userName;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
}
