package com.erico.accessmanagement.business.documentation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de cadastro de usuário")
public interface CreateUserDtoDocumentation {

    @Schema(description = "Nome do usuário", example = "John Doe")
    String name();

    @Schema(description = "E-mail do usuário", example = "john.doa@test.com")
    String email();

    @Schema(description = "Senha de acesso usuário", example = "password123")
    String password();
}
