package com.erico.accessmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de erro de requisição")
public interface ErrorResponseDtoSpec {

    @Schema(description = "Mensagem de erro")
    String message();
}
