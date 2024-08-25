package com.bootcamp.microserviceStock.domain.exception;

public class MaxFieldSizeException extends RuntimeException {
    public MaxFieldSizeException(String message) {
        super(message);
    }
}
