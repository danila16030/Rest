package com.epam.controll.handler;

import com.epam.controll.exception.ArgumentsNotValidException;
import com.epam.daos.exception.NoSuchElementException;
import com.epam.models.dto.ExceptionResponseDTO;
import com.epam.services.exception.InvalidDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ExceptionResponseDTO> hadleInvalidData(final Exception exception) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponseDTO> hadleNoSuchElement() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ArgumentsNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> hadleArgumentsNotValid(final Exception exception) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }
}