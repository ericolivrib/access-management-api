package com.erico.accessmanagement.common;

import com.erico.accessmanagement.business.dto.ErrorResponseDto;
import com.erico.accessmanagement.business.dto.FieldErrorsResponseDto;
import com.erico.accessmanagement.business.exception.EntityAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
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
