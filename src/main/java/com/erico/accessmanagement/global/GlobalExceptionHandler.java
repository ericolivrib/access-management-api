package com.erico.accessmanagement.global;

import com.erico.accessmanagement.dto.ErrorResponseDto;
import com.erico.accessmanagement.dto.FieldErrorsResponseDto;
import com.erico.accessmanagement.exception.ResourceConflictException;
import com.erico.accessmanagement.exception.ResourceGoneException;
import com.erico.accessmanagement.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflict(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(ResourceGoneException.class)
    public ResponseEntity<ErrorResponseDto> handleGone(Exception ex) {
        return ResponseEntity.status(HttpStatus.GONE)
                .body(new ErrorResponseDto(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDto(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
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
