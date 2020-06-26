package com.epam.exception;

public class InvalidDataException extends Exception {
    public InvalidDataException() {
        super("Invalid data in some field");
    }
}
