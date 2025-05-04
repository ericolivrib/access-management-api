package com.erico.accessmanagement.controller;

import com.erico.accessmanagement.dto.CreateUserDto;
import com.erico.accessmanagement.dto.ErrorResponseDto;
import com.erico.accessmanagement.dto.FieldErrorsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Tag(name = "Users", description = "Usuários do sistema")
public interface UserControllerSpec {

    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário com permissão de USER e status NOT_CONFIRMED",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", headers = {
                            @Header(name = HttpHeaders.LOCATION, description = "URI do novo usuário")
                    }),
                    @ApiResponse(responseCode = "409", description = "E-mail já existente", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))
                    }),
                    @ApiResponse(responseCode = "422", description = "Dados inválidos no corpo de requisição", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FieldErrorsResponseDto.class))
                    })
            }
    )
    ResponseEntity<Void> createUser(CreateUserDto createUserDto, UriComponentsBuilder uriBuilder);

    @Operation(
            summary = "Confirmação de usuário",
            description = "Confirma o cadastro de um usuário através de um código de confirmação",
            parameters = @Parameter(
                    name = "code",
                    description = "Código de confirmação",
                    required = true,
                    schema = @Schema(type = MediaType.TEXT_PLAIN_VALUE, implementation = UUID.class)
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Código confirmado com sucesso"),
                    @ApiResponse(responseCode = "410", description = "Código com tempo limite expirado"),
                    @ApiResponse(responseCode = "404", description = "Código de confirmação não encontrado")
            }
    )
    ResponseEntity<Void> confirmUser(UUID codeId);
}
