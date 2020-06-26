package com.epam.handler;

import com.epam.dto.ExceptionResponseDTO;
import com.epam.exception.ArgumentsNotValidException;
import com.epam.exception.InvalidDataException;
import com.epam.exception.NoSuchElementException;
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
    public ResponseEntity<ExceptionResponseDTO> hadleArgumentNotValid(final Exception exception) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }

}