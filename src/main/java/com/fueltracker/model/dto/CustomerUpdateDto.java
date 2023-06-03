package com.fueltracker.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


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
