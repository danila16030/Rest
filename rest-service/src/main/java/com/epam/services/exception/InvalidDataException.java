package com.epam.services.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super("invalid data in some field");
    }
}
