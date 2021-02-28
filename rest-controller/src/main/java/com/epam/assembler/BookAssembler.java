package com.epam.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
import org.jetbrains.annotations.NotNull;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookAssembler extends RepresentationModelAssemblerSupport<Book, BookModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public BookAssembler() {
        super(BookController.class, BookModel.class);
    }

    public BookModel toModel(@NotNull Book entity) {
        BookModel bookModel = mapper.bookToBookModel(entity);
        try {
            bookModel.setImage(Files.readAllBytes(Path.of("/image/" + entity.getImageName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookModel.add(linkTo(
                methodOn(BookController.class)
                        .getBook(entity.getBookId(), new UserPrincipal()))
                .withSelfRel().withName("get"));
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
        bookModels.add(linkTo(methodOn(BookController.class).getResult("", 10, 0, "all",
                new UserPrincipal())).withSelfRel().withName("get all"));
        bookModels.add(linkTo(methodOn(BookController.class).getResult("", 10, 0, "sort",
                new UserPrincipal())).withSelfRel().withName("sort by author"));
        bookModels.add(linkTo(methodOn(BookController.class).getResult("", 10, 0, "full",
                new UserPrincipal())).withSelfRel().withName("search by full coincidence"));
        bookModels.add(linkTo(methodOn(BookController.class).getResult("", 10, 0, "partial",
                new UserPrincipal())).withSelfRel().withName("search by partial coincidence"));
        bookModels.add(linkTo(methodOn(BookController.class).getTopGenre(0, 0, 0,
                new UserPrincipal())).withSelfRel().withName("get top genre"));
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
                .withSelfRel().withName("remove"));
        bookModel.add(linkTo(
                methodOn(BookController.class)
                        .updateBook(new UpdateBookRequestDTO(), new UserPrincipal()))
                .withSelfRel().withName("update"));
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
                    .withSelfRel().withName("remove"));
            genre.add(linkTo(
                    methodOn(GenreController.class)
                            .updateGenre(new UpdateGenreRequestDTO(), new UserPrincipal()))
                    .withSelfRel().withName("update"));
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
                    .withSelfRel().withName("get"));
        }
    }
}
