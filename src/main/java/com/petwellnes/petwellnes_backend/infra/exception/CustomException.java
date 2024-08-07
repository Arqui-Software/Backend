package com.petwellnes.petwellnes_backend.infra.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}