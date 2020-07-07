package com.epam.assembler;

import com.epam.controller.BookController;
import com.epam.controller.GenreController;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Book;
import com.epam.mapper.Mapper;
import com.epam.model.BookModel;
import com.epam.model.GenreModel;
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

    @Override
    public BookModel toModel(Book entity) {
        BookModel bookModel = mapper.bookToBookModel(entity);
        bookModel.add(linkTo(
                methodOn(BookController.class)
                        .getBook(entity.getBookId()))
                .withSelfRel());
        setGenreLinks(bookModel.getGenres());
        bookModel.add(linkTo(
                methodOn(BookController.class)
                        .removeBook(entity.getBookId()))
                .withSelfRel());
        bookModel.add(linkTo(
                methodOn(BookController.class)
                        .updateBook(new UpdateBookRequestDTO()))
                .withSelfRel());
        return bookModel;
    }


    public CollectionModel<BookModel> toCollectionModel(Iterable<? extends Book> entities) {
        CollectionModel<BookModel> actorModels = super.toCollectionModel(entities);

        actorModels.add(linkTo(methodOn(BookController.class).getAllBooks(10, 0)).withSelfRel());
        actorModels.add(linkTo(methodOn(BookController.class).getBooksSortedByDate(10, 0)).withSelfRel());
        actorModels.add(linkTo(methodOn(BookController.class).getBooksSortedByName(10, 0)).withSelfRel());
        actorModels.add(linkTo(methodOn(BookController.class).creteNewBook(new CreateBookRequestDTO())).withSelfRel());
        actorModels.add(linkTo(methodOn(BookController.class).filter(new ParametersRequestDTO(), 10, 0)).withSelfRel());
        actorModels.add(linkTo(methodOn(BookController.class).searchByFullCoincidence(new ParametersRequestDTO(), 10, 0)).withSelfRel());
        actorModels.add(linkTo(methodOn(BookController.class).searchByPartialCoincidence(new ParametersRequestDTO(), 10, 0)).withSelfRel());
        return actorModels;
    }


    private void setGenreLinks(List<GenreModel> genres) {
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
                            .getGenre(genre.getGenreId()))
                    .withSelfRel());
            genre.add(linkTo(
                    methodOn(GenreController.class)
                            .updateGenre(new UpdateGenreRequestDTO()))
                    .withSelfRel());
        }
    }
}
