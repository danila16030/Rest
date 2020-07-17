package com.epam.assembler;

import com.epam.controller.AuthenticationController;
import com.epam.controller.UserController;
import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.entity.User;
import com.epam.mapper.Mapper;
import com.epam.model.UserModel;
import com.epam.principal.UserPrincipal;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public UserAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User entity) {
        UserModel userModel = mapper.userToUserModel(entity);
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .removeUser(new UserPrincipal()))
                .withSelfRel());
        return userModel;
    }

    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> userModels = super.toCollectionModel(entities);
        userModels.add(linkTo(methodOn(UserController.class).getAllUsers(10, 0)).withSelfRel());
        userModels.add(linkTo(methodOn(AuthenticationController.class).singIn(new CreateUserDTO())).withSelfRel());
        userModels.add(linkTo(methodOn(AuthenticationController.class).login(new AuthenticationRequestDTO())).withSelfRel());
        return userModels;
    }


}
