package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ControllerResponse {
    private final String message;
    private final String status;
    private final LocalDateTime timestamp;
}
