package com.bootcamp.microserviceStock.configuration.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private final ArrayList<String> errors;
    private final String status;
    private final LocalDateTime timestamp;
}
