package com.epam.handler;

import com.epam.dto.responce.ExceptionResponseDTO;
import com.epam.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({InvalidDataException.class, ArgumentsNotValidException.class, DuplicatedException.class,
            ForbitenToDelete.class, BadCredentialsException.class})
    public ResponseEntity<ExceptionResponseDTO> hadleInvalidData(final Exception exception) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler({NoSuchElementException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ExceptionResponseDTO> hadleNoSuchElement(final Exception exception) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ExceptionResponseDTO> hadleAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
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