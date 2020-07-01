package com.epam.handler;

import com.epam.dto.responce.ExceptionResponseDTO;
import com.epam.exception.ArgumentsNotValidException;
import com.epam.exception.DuplicatedException;
import com.epam.exception.InvalidDataException;
import com.epam.exception.NoSuchElementException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({InvalidDataException.class, ArgumentsNotValidException.class, DuplicatedException.class})
    public ResponseEntity<ExceptionResponseDTO> hadleInvalidData(final Exception exception) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ExceptionResponseDTO> hadleNoSuchElement() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ExceptionResponseDTO responseDto = new ExceptionResponseDTO(errorMessages.stream()
                .reduce((e1, e2) -> e1 + " || " + e2)
                .orElse("Problems with input data"));
        return ResponseEntity.badRequest().body(responseDto);
    }

}