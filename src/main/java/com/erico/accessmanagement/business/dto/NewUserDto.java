package com.erico.accessmanagement.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewUserDto(
        @NotBlank
        String name,

        @Email
        @NotBlank
        String email,

        // TODO: Create RegEx for password
        @NotBlank
        String password
) {
}
