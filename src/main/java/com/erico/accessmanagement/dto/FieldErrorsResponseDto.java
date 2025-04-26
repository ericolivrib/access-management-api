package com.erico.accessmanagement.dto;

import org.springframework.validation.FieldError;

public record FieldErrorsResponseDto(String field, String message) {

    public FieldErrorsResponseDto(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
