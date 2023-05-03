package com.fueltracker.config.security;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationCredentials {
    @NotBlank(message = "Username can not be blank.")
    private final String username;
    @Email
    @NotBlank(message = "Email can not be blank.")
    private final String email;
    @NotBlank(message = "First name can not be blank.")
    private final String firstName;
    @NotBlank(message = "Last name can not be blank.")
    private final String lastName;
    @NotBlank(message = "Password can not be blank.")
    private final String password;
}
