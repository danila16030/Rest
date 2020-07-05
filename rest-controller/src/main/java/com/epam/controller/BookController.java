package com.epam.controller;

import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
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
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @DeleteMapping(value = "{bookId:[0-9]+}")
    public ResponseEntity<BookResponseDTO> removeBook(@PathVariable long bookId) {
        bookService.removeBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "{bookId:[0-9]+}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable long bookId) {
        return ResponseEntity.ok(bookService.getBook(bookId));
    }

    @PutMapping(value = "/update", headers = {"Accept=application/json"})
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody @Valid UpdateBookRequestDTO bookDTO) {
        BookResponseDTO response = bookService.updateBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getBookId()).build().toUri();
        return ResponseEntity.ok().location(location).body(response);
    }

    @PutMapping(value = "/price", headers = {"Accept=application/json"})
    public ResponseEntity<BookResponseDTO> changePrice(@RequestBody @Valid ParametersRequestDTO parametersDTO) {
        return ResponseEntity.ok(bookService.changeBookPrice(parametersDTO));
    }


    @PostMapping(value = "/create", headers = {"Accept=application/json"})
    public ResponseEntity<BookResponseDTO> creteNewBook(@RequestBody @Valid CreateBookRequestDTO bookDTO) {
        BookResponseDTO response = bookService.createBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getBookId()).build().toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping(value = "{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(@PathVariable int limit, @PathVariable int offset) {
        return ResponseEntity.ok(bookService.getAllBooks(limit, offset));
    }

    @GetMapping(value = "/sorted/by-name/{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<List<BookResponseDTO>> getBooksSortedByName(@PathVariable int limit, @PathVariable int offset) {
        return ResponseEntity.ok(bookService.getBooksSortedByName(limit, offset));
    }

    @GetMapping(value = "/sorted/by-date/{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<List<BookResponseDTO>> getBooksSortedByDate(@PathVariable int offset, @PathVariable int limit) {
        return ResponseEntity.ok(bookService.getBooksSortedByDate(limit, offset));
    }

    @GetMapping(value = "/search/by-partial-coincidence/{limit:[0-9]+},{offset:[0-9]+}", headers = {"Accept=application/json"})
    public ResponseEntity<List<BookResponseDTO>> searchByPartialCoincidence(@RequestBody
                                                                            @Valid ParametersRequestDTO parametersDTO,
                                                                            @PathVariable int limit,
                                                                            @PathVariable int offset) {
        return ResponseEntity.ok(bookService.getBookByPartialCoincidence(parametersDTO, limit, offset));
    }

    @GetMapping(value = "/search/by-full-coincidence/{limit:[0-9]+},{offset:[0-9]+}", headers = {"Accept=application/json"})
    public ResponseEntity<List<BookResponseDTO>> searchByFullCoincidence(@RequestBody
                                                                         @Valid ParametersRequestDTO parametersDTO,
                                                                         @PathVariable int offset,
                                                                         @PathVariable int limit) {
        return ResponseEntity.ok(bookService.getBookByFullCoincidence(parametersDTO, limit, offset));
    }

    @GetMapping(value = "/filter/{limit:[0-9]+},{offset:[0-9]+}", headers = {"Accept=application/json"})
    public ResponseEntity<List<BookResponseDTO>> filter(@RequestBody @Valid ParametersRequestDTO parametersDTO,
                                                        @PathVariable int offset, @PathVariable int limit) {
        return ResponseEntity.ok(bookService.filter(parametersDTO, limit, offset));
    }
}
