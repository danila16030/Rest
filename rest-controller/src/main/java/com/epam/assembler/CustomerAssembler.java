package com.epam.assembler;

import com.epam.controller.GenreController;
import com.epam.controller.UserController;
import com.epam.entity.User;
import com.epam.mapper.Mapper;
import com.epam.model.CustomerModel;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerAssembler extends RepresentationModelAssemblerSupport<User, CustomerModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public CustomerAssembler() {
        super(UserController.class, CustomerModel.class);
    }

    @Override
    public CustomerModel toModel(User entity) {
        CustomerModel userModel = mapper.userToCustomerModel(entity);
        userModel.setFavoriteGenre(mapper.genreToGenreModel(entity.getFavoriteGenre()));
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .getUser(entity.getUserId()))
                .withSelfRel());
        userModel.getFavoriteGenre().add(linkTo(
                methodOn(GenreController.class)
                        .getGenre(entity.getFavoriteGenre().getGenreId()))
                .withSelfRel());
        return userModel;
    }
}
