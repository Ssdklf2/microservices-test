package com.example.productservice.exceptions;

public class InvalidUUIDException extends RuntimeException {
    public InvalidUUIDException(String id) {
        super(String.format("UUID %s is invalid", id));
    }
}
