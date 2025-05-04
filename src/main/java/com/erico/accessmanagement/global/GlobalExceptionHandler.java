package com.erico.accessmanagement.global;

import com.erico.accessmanagement.dto.ErrorResponseDto;
import com.erico.accessmanagement.dto.FieldErrorsResponseDto;
import com.erico.accessmanagement.exception.ResourceConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflict(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Set<FieldErrorsResponseDto>> handleUnprocessableEntity(MethodArgumentNotValidException ex) {
        Set<FieldErrorsResponseDto> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldErrorsResponseDto::new)
                .collect(Collectors.toSet());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(erros);
    }

}
