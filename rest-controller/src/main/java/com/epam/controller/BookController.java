package com.epam.controller;

import com.epam.dto.request.CreateBookRequestDTO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.request.UpdateBookRequestDTO;
import com.epam.dto.responce.BookResponseDTO;
import com.epam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/remove/{bookId}")
    public ResponseEntity<BookResponseDTO> removeBook(@PathVariable long bookId) {
        bookService.removeBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/get/book/{bookId}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable long bookId) {
        return ResponseEntity.ok(bookService.getBook(bookId));
    }

    @PutMapping(value = "/update", headers = {"Accept=application/json"})
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody @Valid UpdateBookRequestDTO bookDTO) {
        BookResponseDTO response = bookService.updateBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getBookId()).build().toUri();
        return ResponseEntity.ok().location(location).body(response);
    }

    @PostMapping(value = "/create", headers = {"Accept=application/json"})
    public ResponseEntity<BookResponseDTO> creteNewBook(@RequestBody @Valid CreateBookRequestDTO bookDTO) {
        BookResponseDTO response = bookService.createBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getBookId()).build().toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping(value = "/get/all")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping(value = "/get/sorted/by-name")
    public ResponseEntity<List<BookResponseDTO>> getBooksSortedByName() {
        return ResponseEntity.ok(bookService.getBooksSortedByName());
    }

    @GetMapping(value = "/get/sorted/by-date")
    public ResponseEntity<List<BookResponseDTO>> getBooksSortedByDate() {
        return ResponseEntity.ok(bookService.getBooksSortedByDate());
    }

    @GetMapping(value = "/search/by-partial-coincidence", headers = {"Accept=application/json"})
    public ResponseEntity<List<BookResponseDTO>> searchByPartialCoincidence(@RequestBody @Valid ParametersRequestDTO parametersDTO) {
        return ResponseEntity.ok(bookService.getBookByPartialCoincidence(parametersDTO));
    }

    @GetMapping(value = "/search/by-full-coincidence", headers = {"Accept=application/json"})
    public ResponseEntity<List<BookResponseDTO>> searchByFullCoincidence(@RequestBody @Valid ParametersRequestDTO parametersDTO) {
        return ResponseEntity.ok(bookService.getBookByFullCoincidence(parametersDTO));
    }

    @GetMapping(value = "/filter", headers = {"Accept=application/json"})
    public ResponseEntity<List<BookResponseDTO>> filterByDate(@RequestBody @Valid ParametersRequestDTO parametersDTO) {
        return ResponseEntity.ok(bookService.filter(parametersDTO));
    }
}
