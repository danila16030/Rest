package com.epam.dto.responce;

public class ExceptionResponseDTO{
    private String message;

    public ExceptionResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
