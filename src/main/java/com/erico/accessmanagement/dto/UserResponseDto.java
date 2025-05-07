package com.erico.accessmanagement.dto;

import com.erico.accessmanagement.model.UserStatus;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        UserStatus status
) {
}
