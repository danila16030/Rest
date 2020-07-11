package com.epam.controller;

import com.epam.assembler.BookAssembler;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.entity.Book;
import com.epam.model.BookModel;
import com.epam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookAssembler bookAssembler;


    @DeleteMapping(value = "{bookId:[0-9]+}")
    public ResponseEntity<BookModel> removeBook(@PathVariable long bookId) {
        bookService.removeBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "{bookId:[0-9]+}")
    public ResponseEntity<BookModel> getBook(@PathVariable long bookId) {
        return ResponseEntity.ok(bookAssembler.toModel(bookService.getBook(bookId)));
    }

    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity<BookModel> updateBook(@RequestBody @Valid UpdateBookRequestDTO bookDTO) {
        Book response = bookService.updateBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getBookId()).build().toUri();
        return ResponseEntity.ok().location(location).body(bookAssembler.toModel(response));
    }

    @PutMapping(value = "/price", headers = {"Accept=application/json"})
    public ResponseEntity<BookModel> changePrice(@RequestBody @Valid ParametersRequestDTO parametersDTO) {
        return ResponseEntity.ok(bookAssembler.toModel(bookService.changeBookPrice(parametersDTO)));
    }


    @PostMapping(headers = {"Accept=application/json"})
    public ResponseEntity<BookModel> creteNewBook(@RequestBody @Valid CreateBookRequestDTO bookDTO) {
        Book response = bookService.createBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getBookId()).build().toUri();
        return ResponseEntity.created(location).body(bookAssembler.toModel(response));
    }

    @GetMapping(value = "{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> getAllBooks(@PathVariable int limit, @PathVariable int offset) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getAllBooks(limit, offset)));
    }

    @GetMapping(value = "/by-name/{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> getBooksSortedByName(@PathVariable int limit, @PathVariable int offset) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getBooksSortedByName(limit, offset)));
    }

    @GetMapping(value = "/by-date/{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> getBooksSortedByDate(@PathVariable int offset, @PathVariable int limit) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getBooksSortedByDate(limit, offset)));
    }

    @GetMapping(value = "/by-partial-coincidence/{title},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> searchByPartialCoincidence(@PathVariable String title,
                                                                                 @PathVariable int limit,
                                                                                 @PathVariable int offset) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getBookByPartialCoincidence(title,
                limit, offset)));
    }

    @GetMapping(value = "/by-full-coincidence/{title},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> searchByFullCoincidence(@PathVariable String title,
                                                                              @PathVariable int offset,
                                                                              @PathVariable int limit) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getBookByFullCoincidence(title,
                limit, offset)));
    }


}
