package com.erico.accessmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de cadastro de usuário")
public interface CreateUserDtoSpec {

    @Schema(description = "Nome do usuário", example = "John Doe")
    String name();

    @Schema(description = "E-mail do usuário", example = "john.doe@test.com")
    String email();

    @Schema(description = "Senha de acesso usuário", example = "password123")
    String password();
}
