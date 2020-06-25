package com.epam.controll.exception;

public class ArgumentsNotValidException extends RuntimeException {
    public ArgumentsNotValidException() {
        super("not valid argument");
    }
}
