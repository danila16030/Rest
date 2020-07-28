package com.epam.assembler;

import com.epam.controller.BookController;
import com.epam.controller.DebugController;
import com.epam.controller.GenreController;
import com.epam.controller.UserController;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.dto.request.update.updateUserRequestDTO;
import com.epam.entity.*;
import com.epam.mapper.Mapper;
import com.epam.model.DebugModel;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DebugAssembler extends RepresentationModelAssemblerSupport<Auditable, DebugModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public DebugAssembler() {
        super(DebugController.class, DebugModel.class);
    }

    public DebugModel toModel(Book entity) {
        DebugModel debugModel = mapper.auditableToDebugModel(entity);
        setInnerModel(debugModel, entity.getGenres());
        setModelLinks(debugModel.getInnerModels(), GenreController.class);
        debugModel.setId(entity.getBookId());
        debugModel.add(linkTo(
                methodOn(BookController.class)
                        .updateBook(new UpdateBookRequestDTO()))
                .withSelfRel());
        return debugModel;
    }

    public DebugModel toModel(Genre entity) {
        DebugModel debugModel = mapper.auditableToDebugModel(entity);
        debugModel.setId(entity.getGenreId());
        debugModel.add(linkTo(
                methodOn(GenreController.class)
                        .updateGenre(new UpdateGenreRequestDTO()))
                .withSelfRel());
        return debugModel;
    }

    public DebugModel toModel(User entity) {
        DebugModel debugModel = mapper.auditableToDebugModel(entity);
        debugModel.setId(entity.getUserId());
        debugModel.add(linkTo(
                methodOn(UserController.class)
                        .update(new updateUserRequestDTO()))
                .withSelfRel());
        return debugModel;
    }

    public DebugModel toModel(Order entity) {
        DebugModel debugModel = mapper.auditableToDebugModel(entity);
        debugModel.setId(entity.getOrderId());
        return debugModel;
    }

    @Override
    public DebugModel toModel(Auditable entity) {
        return null;
    }

    private void setInnerModel(DebugModel debugModel, List<Genre> list) {
        if (list != null) {
            for (Genre genre : list) {
                DebugModel debugModel1 = mapper.auditableToDebugModel(genre);
                debugModel1.setId(genre.getGenreId());
                debugModel1.getInnerModels().add(debugModel);
            }
        }
    }

    private void setModelLinks(List<DebugModel> debugModels, Class<GenreController> tClass) {
        if (debugModels != null) {
            for (DebugModel debugModel : debugModels) {
                debugModel.add(linkTo(
                        methodOn(tClass)
                                .updateGenre(new UpdateGenreRequestDTO()))
                        .withSelfRel());
            }
        }
    }
}
