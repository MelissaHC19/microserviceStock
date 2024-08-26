package com.bootcamp.microserviceStock.configuration.exceptionHandler;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.exception.BrandAlreadyExistsException;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.bootcamp.microserviceStock.domain.exception.EmptyFieldException;
import com.bootcamp.microserviceStock.domain.exception.MaxFieldSizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(EmptyFieldException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(String.format(e.getMessage()), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MaxFieldSizeException.class)
    public ResponseEntity<ExceptionResponse> handleMaxFieldSizeException(MaxFieldSizeException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(String.format(e.getMessage()), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(String.format(e.getMessage()), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleBrandAlreadyExistsException(BrandAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(String.format(e.getMessage()), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
}