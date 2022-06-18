package com.fueltracker.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CustomerUpdateDto {
    @NotBlank(message = "Username can not be blank.")
    private String username;
    @Email
    @NotBlank(message = "Email can not be blank.")
    private String email;
    @NotBlank(message = "First name an not be blank.")
    private String firstName;
    @NotBlank(message = "Last name can not be blank.")
    private String lastName;
}
