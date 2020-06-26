package com.epam.exception;

public class ArgumentsNotValidException extends RuntimeException {
    public ArgumentsNotValidException() {
        super("Not valid argument");
    }
}
