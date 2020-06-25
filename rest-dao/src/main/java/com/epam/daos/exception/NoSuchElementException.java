package com.epam.daos.exception;

public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException() {
        super("There is no such element");
    }
}
