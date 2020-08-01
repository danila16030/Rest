package com.epam.assembler;

import com.epam.controller.BookController;
import com.epam.controller.GenreController;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Book;
import com.epam.mapper.Mapper;
import com.epam.model.BookModel;
import com.epam.model.GenreModel;
import com.epam.principal.UserPrincipal;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookAssembler extends RepresentationModelAssemblerSupport<Book, BookModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public BookAssembler() {
        super(BookController.class, BookModel.class);
    }


    public BookModel toModel(Book entity) {
        BookModel bookModel = mapper.bookToBookModel(entity);
        bookModel.add(linkTo(
                methodOn(BookController.class)
                        .getBook(entity.getBookId(), new UserPrincipal()))
                .withSelfRel());
        addGenreLinks(bookModel.getGenres());
        return bookModel;
    }

    public CollectionModel<BookModel> toCollection(Iterable<? extends Book> entities, UserPrincipal principal) {
        CollectionModel<BookModel> bookModels = super.toCollectionModel(entities);
        if (principal != null && principal.getRole().equals("ADMIN")) {
            bookModels.forEach(bookModel -> bookModel = addAdminLinks(bookModel));
        }
        return bookModels;
    }

    public CollectionModel<BookModel> toCollectionModel(Iterable<? extends Book> entities, UserPrincipal principal) {
        CollectionModel<BookModel> bookModels = super.toCollectionModel(entities);
        if (principal != null && principal.getRole().equals("ADMIN")) {
            bookModels.forEach(bookModel -> bookModel = addAdminLinks(bookModel));
            bookModels.add(linkTo(methodOn(BookController.class).creteNewBook(new CreateBookRequestDTO(),
                    new UserPrincipal())).withSelfRel());
            bookModels.add(linkTo(methodOn(BookController.class).removeBook(0)).withSelfRel());
        }
        bookModels.add(linkTo(methodOn(BookController.class).getAllBooks(10, 0,
                new UserPrincipal())).withSelfRel());
        bookModels.add(linkTo(methodOn(BookController.class).getBooksSortedByDate(10, 0,
                new UserPrincipal())).withSelfRel());
        bookModels.add(linkTo(methodOn(BookController.class).getBooksSortedByName(10, 0,
                new UserPrincipal())).withSelfRel());
        bookModels.add(linkTo(methodOn(BookController.class).searchByFullCoincidence("", 10, 0,
                new UserPrincipal())).withSelfRel());
        bookModels.add(linkTo(methodOn(BookController.class).searchByPartialCoincidence("", 10, 0,
                new UserPrincipal())).withSelfRel());
        bookModels.add(linkTo(methodOn(BookController.class).getTopGenre(0, 0, 0)).withSelfRel());
        return bookModels;
    }

    public BookModel toBookModel(Book entity, UserPrincipal principal) {
        BookModel bookModel = toModel(entity);
        if (principal != null && principal.getRole().equals("ADMIN")) {
            return addAdminLinks(bookModel);
        }
        return bookModel;
    }

    private BookModel addAdminLinks(BookModel bookModel) {
        bookModel.add(linkTo(
                methodOn(BookController.class)
                        .removeBook(bookModel.getBookId()))
                .withSelfRel());
        bookModel.add(linkTo(
                methodOn(BookController.class)
                        .updateBook(new UpdateBookRequestDTO(), new UserPrincipal()))
                .withSelfRel());
        addAdminGenreLinks(bookModel.getGenres());
        return bookModel;
    }

    private void addAdminGenreLinks(List<GenreModel> genres) {
        if (genres.isEmpty()) {
            return;
        }
        for (GenreModel genre : genres) {
            genre.add(linkTo(
                    methodOn(GenreController.class)
                            .removeGenre(genre.getGenreId()))
                    .withSelfRel());
            genre.add(linkTo(
                    methodOn(GenreController.class)
                            .updateGenre(new UpdateGenreRequestDTO(), new UserPrincipal()))
                    .withSelfRel());
        }
    }

    private void addGenreLinks(List<GenreModel> genres) {
        if (genres.isEmpty()) {
            return;
        }
        for (GenreModel genre : genres) {
            genre.add(linkTo(
                    methodOn(GenreController.class)
                            .getGenre(genre.getGenreId(), new UserPrincipal()))
                    .withSelfRel());
        }
    }
}
