package com.bootcamp.microserviceStock.configuration.exceptionHandler;

import com.bootcamp.microserviceStock.domain.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptionUC(ValidationException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getErrors(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptionsDTO(MethodArgumentNotValidException ex) {
        ArrayList<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.add(error.getDefaultMessage())
        );
        ExceptionResponse response = new ExceptionResponse(errors, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }
}