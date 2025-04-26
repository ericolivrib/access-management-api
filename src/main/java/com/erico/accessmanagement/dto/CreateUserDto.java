package com.erico.accessmanagement.dto;

import com.erico.accessmanagement.documentation.CreateUserDtoDocumentation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto (
        @NotBlank
        String name,

        @Email
        @NotBlank
        String email,

        // TODO: Create RegEx for password
        @NotBlank
        @Size(min = 8)
        String password
) implements CreateUserDtoDocumentation {
}
