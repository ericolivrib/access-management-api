package com.erico.accessmanagement.dto;

import com.erico.accessmanagement.documentation.ErrorResponseDtoDocumentation;

public record ErrorResponseDto(String message) implements ErrorResponseDtoDocumentation {
}
