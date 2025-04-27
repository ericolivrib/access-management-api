package com.erico.accessmanagement.dto;

import com.erico.accessmanagement.documentation.FieldErrorsResponseDtoDocumentation;
import org.springframework.validation.FieldError;

public record FieldErrorsResponseDto(String field, String message) implements FieldErrorsResponseDtoDocumentation {

    public FieldErrorsResponseDto(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
