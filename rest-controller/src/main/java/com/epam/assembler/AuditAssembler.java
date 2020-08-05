package com.epam.assembler;

import com.epam.controller.AuditController;
import com.epam.controller.BookController;
import com.epam.controller.GenreController;
import com.epam.controller.UserController;
import com.epam.entity.*;
import com.epam.mapper.Mapper;
import com.epam.model.AuditModel;
import com.epam.principal.UserPrincipal;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuditAssembler extends RepresentationModelAssemblerSupport<Auditable, AuditModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public AuditAssembler() {
        super(AuditController.class, AuditModel.class);
    }

    public AuditModel toModel(Book entity) {
        AuditModel model = mapper.auditableToDebugModel(entity);
        setInnerModel(model, entity.getGenres());
        setModelLinks(model.getInnerModels(), GenreController.class);
        model.setId(entity.getBookId());
        model.add(linkTo(
                methodOn(BookController.class)
                        .getBook(model.getId(), new UserPrincipal()))
                .withSelfRel().withName("get"));
        return model;
    }

    public AuditModel toModel(Genre entity) {
        AuditModel auditModel = mapper.auditableToDebugModel(entity);
        auditModel.setId(entity.getGenreId());
        auditModel.add(linkTo(
                methodOn(GenreController.class)
                        .getGenre(auditModel.getId(), new UserPrincipal()))
                .withSelfRel().withName("get"));
        return auditModel;
    }

    public AuditModel toModel(User entity) {
        AuditModel auditModel = mapper.auditableToDebugModel(entity);
        auditModel.setId(entity.getUserId());
        auditModel.add(linkTo(
                methodOn(UserController.class)
                        .getSomeUser(auditModel.getId()))
                .withSelfRel().withName("get"));
        return auditModel;
    }

    public AuditModel toModel(Order entity) {
        AuditModel auditModel = mapper.auditableToDebugModel(entity);
        auditModel.setId(entity.getOrderId());
        return auditModel;
    }

    @Override
    public AuditModel toModel(Auditable entity) {
        return null;
    }

    private void setInnerModel(AuditModel auditModel, List<Genre> list) {
        if (list != null) {
            for (Genre genre : list) {
                AuditModel auditModel1 = mapper.auditableToDebugModel(genre);
                auditModel1.setId(genre.getGenreId());
                auditModel1.getInnerModels().add(auditModel);
            }
        }
    }

    private void setModelLinks(List<AuditModel> auditModels, Class<GenreController> tClass) {
        if (auditModels != null) {
            for (AuditModel auditModel : auditModels) {
                auditModel.add(linkTo(
                        methodOn(tClass)
                                .getGenre(auditModel.getId(), new UserPrincipal()))
                        .withSelfRel().withName("get"));
            }
        }
    }
}
