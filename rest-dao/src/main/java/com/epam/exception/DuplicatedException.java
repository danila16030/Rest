package com.epam.exception;

public class DuplicatedException extends RuntimeException {
    public DuplicatedException() {
        super("Genre with this name is already exist");
    }
}
