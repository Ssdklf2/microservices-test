package com.example.productservice.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resource, String id) {
        super(String.format("%s by id %s not found", resource, id));
    }
}
