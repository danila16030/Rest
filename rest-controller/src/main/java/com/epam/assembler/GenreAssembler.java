package com.epam.assembler;

import com.epam.controller.GenreController;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Genre;
import com.epam.mapper.Mapper;
import com.epam.model.GenreModel;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GenreAssembler extends RepresentationModelAssemblerSupport<Genre, GenreModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public GenreAssembler() {
        super(GenreController.class, GenreModel.class);
    }

    @Override
    public GenreModel toModel(Genre entity) {
        GenreModel userModel = mapper.genreToGenreModel(entity);

        userModel.add(linkTo(
                methodOn(GenreController.class)
                        .removeGenre(entity.getGenreId()))
                .withSelfRel());
        userModel.add(linkTo(
                methodOn(GenreController.class)
                        .getGenre(entity.getGenreId()))
                .withSelfRel());
        userModel.add(linkTo(
                methodOn(GenreController.class)
                        .updateGenre(new UpdateGenreRequestDTO()))
                .withSelfRel());

        return userModel;
    }

    public CollectionModel<GenreModel> toCollectionModel(Iterable<? extends Genre> entities) {
        CollectionModel<GenreModel> genreModels = super.toCollectionModel(entities);
        genreModels.add(linkTo(methodOn(GenreController.class).creteNewGenre(new CreateGenreRequestDTO())).withSelfRel());
        genreModels.add(linkTo(methodOn(GenreController.class).getAllGenres(10, 0)).withSelfRel());
        return genreModels;
    }
}
