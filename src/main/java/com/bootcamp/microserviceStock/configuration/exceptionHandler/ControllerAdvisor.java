package com.bootcamp.microserviceStock.configuration.exceptionHandler;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.bootcamp.microserviceStock.domain.exception.EmptyFieldException;
import com.bootcamp.microserviceStock.domain.exception.MaxFieldSizeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "message";

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<Map<String, String>> handleEmptyFieldException(EmptyFieldException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }

    @ExceptionHandler(MaxFieldSizeException.class)
    public ResponseEntity<Map<String, String>> handleMaxFieldSizeException(MaxFieldSizeException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }
}