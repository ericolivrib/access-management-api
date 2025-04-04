package com.erico.accessmanagement.auth.dto;

import java.time.Instant;
import java.time.LocalDate;

import static com.erico.accessmanagement.auth.config.JwtConfig.JWT_EXPIRATION_TIME;

public record JwtResponseDto(String token, Instant expiresIn) {

    public JwtResponseDto(String token) {
        this(token, Instant.now().plusSeconds(JWT_EXPIRATION_TIME));
    }
}
