package com.epam.controller;

import com.epam.assembler.DebugAssembler;
import com.epam.entity.OrderUser;
import com.epam.model.DebugModel;
import com.epam.service.BookService;
import com.epam.service.GenreService;
import com.epam.service.OrderService;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/debug")
@PreAuthorize("hasAuthority('ADMIN')")
public class DebugController {

    private BookService bookService;

    private GenreService genreService;

    private OrderService orderService;

    private UserService userService;

    @Autowired
    public DebugController(BookService bookService, GenreService genreService, OrderService orderService,
                           UserService userService, DebugAssembler debugAssembler) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.orderService = orderService;
        this.userService = userService;
        this.debugAssembler = debugAssembler;
    }

    private DebugAssembler debugAssembler;

    @GetMapping(value = "books/{bookId:[0-9]+}")
    public ResponseEntity<DebugModel> getBook(@PathVariable long bookId) {
        return ResponseEntity.ok(debugAssembler.toModel(bookService.getBook(bookId)));
    }

    @GetMapping(value = "genres/{genreId:[0-9]+}")
    public ResponseEntity<DebugModel> getGenre(@PathVariable long genreId) {
        return ResponseEntity.ok(debugAssembler.toModel(genreService.getGenre(genreId)));
    }

    @GetMapping(value = "orders/{userId:[0-9]+},{orderId:[0-9]+}")
    public ResponseEntity<DebugModel> getOrder(@PathVariable long orderId, @PathVariable long userId) {
        return ResponseEntity.ok(debugAssembler.toModel(orderService.getOrder(new OrderUser(userId, orderId))));
    }

    @GetMapping(value = "users/{username}")
    public ResponseEntity<DebugModel> getUser(@PathVariable String username) {
        return ResponseEntity.ok(debugAssembler.toModel(userService.getUser(username)));
    }

}
