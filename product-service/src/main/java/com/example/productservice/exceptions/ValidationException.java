package com.example.productservice.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(String.format("Validation error: %s", message));
    }
}
