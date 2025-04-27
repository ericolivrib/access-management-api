package com.erico.accessmanagement.documentation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciais de login")
public interface SignInDtoDocumentation {

    @Schema(description = "E-mail do usuário", example = "john.doe@test.com")
    String email();

    @Schema(description = "Senha de acesso", example = "password123")
    String password();
}
