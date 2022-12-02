package com.example.productservice.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resource, UUID id) {
        super(String.format("%s by id %s not found", resource, id));
    }
}
