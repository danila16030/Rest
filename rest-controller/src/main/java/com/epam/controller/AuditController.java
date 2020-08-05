package com.epam.controller;

import com.epam.assembler.AuditAssembler;
import com.epam.entity.OrderUser;
import com.epam.model.AuditModel;
import com.epam.service.BookService;
import com.epam.service.GenreService;
import com.epam.service.OrderService;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/audit")
@PreAuthorize("hasAuthority('ADMIN')")
public class AuditController {

    private BookService bookService;

    private GenreService genreService;

    private OrderService orderService;

    private UserService userService;

    @Autowired
    public AuditController(BookService bookService, GenreService genreService, OrderService orderService,
                           UserService userService, AuditAssembler auditAssembler) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.orderService = orderService;
        this.userService = userService;
        this.auditAssembler = auditAssembler;
    }

    private AuditAssembler auditAssembler;

    @GetMapping(value = "books")
    public ResponseEntity<AuditModel> getBook(@RequestParam long bookId) {
        return ResponseEntity.ok(auditAssembler.toModel(bookService.getBook(bookId)));
    }

    @GetMapping(value = "genres")
    public ResponseEntity<AuditModel> getGenre(@RequestParam long genreId) {
        return ResponseEntity.ok(auditAssembler.toModel(genreService.getGenre(genreId)));
    }

    @GetMapping(value = "orders")
    public ResponseEntity<AuditModel> getOrder(@RequestParam long userId, @RequestParam() long orderId) {
        return ResponseEntity.ok(auditAssembler.toModel(orderService.getOrder(new OrderUser(userId, orderId))));
    }

    @GetMapping(value = "users")
    public ResponseEntity<AuditModel> getUser(@RequestParam String username) {
        return ResponseEntity.ok(auditAssembler.toModel(userService.getUser(username)));
    }

}
