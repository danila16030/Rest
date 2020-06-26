package com.epam.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super("Invalid data in some field");
    }
}
