package com.example.productservice.exceptions;

public class NoDataException extends RuntimeException {
    public NoDataException(String resources) {
        super(String.format("%s not found", resources));
    }
}
