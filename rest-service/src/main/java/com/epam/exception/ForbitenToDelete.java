package com.epam.exception;

public class ForbitenToDelete extends RuntimeException {
    public ForbitenToDelete(String message) {
        super(message);
    }
}
