package com.erico.accessmanagement.documentation;

import com.erico.accessmanagement.dto.FieldErrorsResponseDto;
import com.erico.accessmanagement.dto.JwtResponseDto;
import com.erico.accessmanagement.dto.SignInDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Login", description = "Log-in do sistema")
public interface LoginControllerDocumentation {

    @Operation(
            summary = "Log-in de usuário",
            description = "Autenticação do sistema. Retorna um token JWT com sua data de expiração",
            requestBody = @RequestBody(
                    description = "Credenciais de login do usuário",
                    required = true,
                    content = @Content(schema = @Schema(type = MediaType.APPLICATION_JSON_VALUE, implementation = SignInDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtResponseDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Usuário não autorizado - Credenciais inválidas"),
                    @ApiResponse(responseCode = "422", description = "Erro de Validação - Campos de login com erros semânticos", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FieldErrorsResponseDto.class))
                    }),
            }
    )
    ResponseEntity<JwtResponseDto> signIn(SignInDto signInDto);
}
