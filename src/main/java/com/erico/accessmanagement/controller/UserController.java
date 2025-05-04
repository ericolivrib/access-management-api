package com.erico.accessmanagement.controller;

import com.erico.accessmanagement.documentation.UserControllerDocumentation;
import com.erico.accessmanagement.dto.CreateUserDto;
import com.erico.accessmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController implements UserControllerDocumentation {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDto createUserDto, UriComponentsBuilder uriBuilder) {
        UUID userId = userService.createUser(createUserDto);

        String uri = uriBuilder.path("/v1/users/{id}").buildAndExpand(userId).toUriString();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, uri)
                .build();
    }

    @GetMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmUser(@RequestParam("code") UUID codeId) {
        userService.confirmUser(codeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        // TODO: Redirecionar para página em branco.
    }
}
