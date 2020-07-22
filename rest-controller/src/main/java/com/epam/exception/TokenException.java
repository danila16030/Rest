package com.epam.exception;

public class TokenException extends RuntimeException {
    public TokenException() {
        super("Expired or invalid JWT token");
    }
}
