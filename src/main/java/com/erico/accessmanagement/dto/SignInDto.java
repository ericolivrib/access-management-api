package com.erico.accessmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInDto(
        @Email(message = "Formato de e-mail inválido")
        @NotBlank(message = "E-mail não pode estar vazio")
        String email,

        @NotBlank(message = "E-mail não pode estar vazio")
        String password
) implements SignInDtoSpec {
}
