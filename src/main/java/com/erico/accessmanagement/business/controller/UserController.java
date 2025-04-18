package com.erico.accessmanagement.business.controller;

import com.erico.accessmanagement.business.dto.NewUserDto;
import com.erico.accessmanagement.business.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid NewUserDto newUserDto, UriComponentsBuilder uriBuilder) {
        UUID userId = userService.createUser(newUserDto);

        String uri = uriBuilder.path("/v1/users/{id}").buildAndExpand(userId).toUriString();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, uri)
                .build();
    }
}
