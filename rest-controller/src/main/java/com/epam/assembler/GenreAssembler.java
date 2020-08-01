package com.epam.assembler;

import com.epam.controller.GenreController;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Genre;
import com.epam.mapper.Mapper;
import com.epam.model.GenreModel;
import com.epam.principal.UserPrincipal;
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
        GenreModel genreModel = mapper.genreToGenreModel(entity);
        genreModel.add(linkTo(
                methodOn(GenreController.class)
                        .getGenre(entity.getGenreId(), new UserPrincipal()))
                .withSelfRel());
        return genreModel;
    }

    public GenreModel toGenreModel(Genre entity, UserPrincipal principal) {
        GenreModel genreModel = toModel(entity);
        if (principal != null && principal.getRole().equals("ADMIN")) {
            return addAdminLinks(genreModel);
        }
        return genreModel;
    }

    public CollectionModel<GenreModel> toCollection(Iterable<? extends Genre> entities, UserPrincipal principal) {
        CollectionModel<GenreModel> genreModels = super.toCollectionModel(entities);
        if (principal != null && principal.getRole().equals("ADMIN")) {
            genreModels.forEach(genreModel -> genreModel = addAdminLinks(genreModel));
        }
        return genreModels;
    }

    public CollectionModel<GenreModel> toCollectionModel(Iterable<? extends Genre> entities, UserPrincipal principal) {
        CollectionModel<GenreModel> genreModels = super.toCollectionModel(entities);
        if (principal != null && principal.getRole().equals("ADMIN")) {
            genreModels.forEach(genreModel -> genreModel = addAdminLinks(genreModel));
            genreModels.add(linkTo(methodOn(GenreController.class).
                    creteNewGenre(new CreateGenreRequestDTO(), new UserPrincipal())).withSelfRel());
            genreModels.add(linkTo(methodOn(GenreController.class).removeGenre(0)).withSelfRel());
        }

        genreModels.add(linkTo(methodOn(GenreController.class).getAllGenres(10, 0, new UserPrincipal())).
                withSelfRel());
        return genreModels;
    }

    private GenreModel addAdminLinks(GenreModel genreModel) {
        genreModel.add(linkTo(
                methodOn(GenreController.class)
                        .removeGenre(genreModel.getGenreId()))
                .withSelfRel());
        genreModel.add(linkTo(
                methodOn(GenreController.class)
                        .updateGenre(new UpdateGenreRequestDTO(), new UserPrincipal()))
                .withSelfRel());
        return genreModel;
    }
}
