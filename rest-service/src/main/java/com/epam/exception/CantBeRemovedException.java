package com.epam.exception;

public class CantBeRemovedException extends RuntimeException {
    public CantBeRemovedException(String message) {
        super(message);
    }
}
