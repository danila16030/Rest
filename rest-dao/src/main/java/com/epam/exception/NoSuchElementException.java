package com.epam.exception;

public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException() {
        super("There is no such element");
    }
    public NoSuchElementException(String message) {
        super(message);
    }
}
