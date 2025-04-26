package com.erico.accessmanagement.documentation;

import com.erico.accessmanagement.dto.CreateUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Users", description = "Usuários do sistema")
public interface UserControllerDocumentation {

    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário com permissão de USER e status NOT_CONFIRMED",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
                    @ApiResponse(responseCode = "409", description = "E-mail já existente"),
                    @ApiResponse(responseCode = "422", description = "Dados inválidos no corpo de requisição")
            }
    )
    ResponseEntity<Void> createUser(CreateUserDto createUserDto, UriComponentsBuilder uriBuilder);

}
