package com.erico.accessmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Erros dos campos de dados de requisição")
public interface FieldErrorsResponseDtoSpec {
    @Schema(description = "Nome do campo", example = "password")
    String field();

    @Schema(description = "Mensagem de erro", example = "A senha deve ter no mínimo 8 caracteres")
    String message();
}
