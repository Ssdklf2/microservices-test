package com.example.jobservice.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("Not found by id: " + id);
    }
}
