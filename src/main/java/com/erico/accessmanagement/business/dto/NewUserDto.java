package com.erico.accessmanagement.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDto(
        @NotBlank
        String name,

        @Email
        @NotBlank
        String email,

        // TODO: Create RegEx for password
        @NotBlank
        @Size(min = 8)
        String password
) {
}
