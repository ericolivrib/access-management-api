package com.erico.accessmanagement.dto;

import com.erico.accessmanagement.documentation.CreateUserDtoDocumentation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto (
        @NotBlank(message = "Nome não pode estar vazio")
        String name,

        @Email(message = "Formato de e-mail inválido")
        @NotBlank(message = "E-mail não pode estar vazio")
        String email,

        // TODO: Create RegEx for password
        @NotBlank(message = "Senha não pode estar vazia")
        @Size(min = 8, message = "A senha deve ter, no mínimo, 8 caracteres")
        String password
) implements CreateUserDtoDocumentation {
}
