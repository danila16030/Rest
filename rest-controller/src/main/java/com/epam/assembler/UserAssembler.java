package com.epam.assembler;

import com.epam.controller.OrderController;
import com.epam.controller.UserController;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.entity.User;
import com.epam.mapper.Mapper;
import com.epam.model.OrderModel;
import com.epam.model.UserModel;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

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
                        .getUser(entity.getUserId()))
                .withSelfRel());
        setOrderLinks(userModel.getOrders(), userModel.getUserId());
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .removeUser(entity.getUserId()))
                .withSelfRel());
        return userModel;
    }

    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> userModels = super.toCollectionModel(entities);

        userModels.add(linkTo(methodOn(UserController.class).getAllUsers(10, 0)).withSelfRel());
        userModels.add(linkTo(methodOn(UserController.class).getTopUser()).withSelfRel());
        userModels.add(linkTo(methodOn(UserController.class).createUser(new CreateUserDTO())).withSelfRel());
        return userModels;
    }

    private void setOrderLinks(List<OrderModel> orders, long userId) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        for (OrderModel order : orders) {
            order.add(linkTo(
                    methodOn(OrderController.class)
                            .removeOrder(userId, order.getOrderId()))
                    .withSelfRel());
            order.add(linkTo(
                    methodOn(OrderController.class)
                            .getOrder(userId, order.getOrderId()))
                    .withSelfRel());
        }
    }
}
