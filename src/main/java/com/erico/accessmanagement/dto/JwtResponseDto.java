package com.erico.accessmanagement.dto;

import java.time.Instant;

import static com.erico.accessmanagement.config.JwtConfig.JWT_EXPIRATION_TIME;

public record JwtResponseDto(String token, Instant expiresIn) implements JwtResponseDtoSpec {

    public JwtResponseDto(String token) {
        this(token, Instant.now().plusSeconds(JWT_EXPIRATION_TIME));
    }
}
