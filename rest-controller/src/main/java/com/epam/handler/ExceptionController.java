package com.epam.handler;

import com.epam.exception.ArgumentsNotValidException;
import com.epam.exception.NoSuchElementException;
import com.epam.dto.ExceptionResponseDTO;
import com.epam.exception.InvalidDataException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {
//    @ResponseStatus(HttpStatus.CONFLICT)
//    @ExceptionHandler(InvalidDataException.class)
//    public ModelAndView hadleInvalidData(HttpServletRequest request, Exception e) throws Exception {
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//            throw e;
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", request.getRequestURL());
//        mav.setViewName("error");
//        return mav;
//    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ExceptionResponseDTO> hadleInvalidData(final Exception exception) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponseDTO> hadleNoSuchElement() {
        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ArgumentsNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> hadleArgumentNotValid(final Exception exception) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }

}