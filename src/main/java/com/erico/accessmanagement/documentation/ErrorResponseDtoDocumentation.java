package com.erico.accessmanagement.documentation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de erro de requisição")
public interface ErrorResponseDtoDocumentation {

    @Schema(description = "Mensagem de erro")
    String message();
}
