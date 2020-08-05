package com.epam.assembler;

import com.epam.controller.UserController;
import com.epam.dto.request.update.updateUserRequestDTO;
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
                .withSelfRel().withName("remove"));
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .update(new updateUserRequestDTO(), new UserPrincipal()))
                .withSelfRel().withName("update"));
        return userModel;
    }

    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> userModels = super.toCollectionModel(entities);
        userModels.forEach(userModel -> userModel = addAdminLinks(userModel));
        userModels.add(linkTo(methodOn(UserController.class).getAllUsers(10, 0)).withSelfRel().
                withName("get users"));
        userModels.add(linkTo(methodOn(UserController.class).getSomeUser(0)).withSelfRel().withName("get some user"));
        userModels.add(linkTo(methodOn(UserController.class).removeSomeUser(0)).withSelfRel().
                withName("remove some user"));
        userModels.add(linkTo(methodOn(UserController.class).getUser(new UserPrincipal())).withSelfRel().
                withName("get some user"));
        return userModels;
    }

    private UserModel addAdminLinks(UserModel userModel) {
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .removeSomeUser(userModel.getUserId()))
                .withSelfRel().withName("remove some user"));
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .update(new updateUserRequestDTO(), 0))
                .withSelfRel().withName("update some user"));
        return userModel;
    }
}
