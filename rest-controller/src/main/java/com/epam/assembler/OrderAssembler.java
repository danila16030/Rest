package com.epam.assembler;

import com.epam.controller.BookController;
import com.epam.controller.OrderController;
import com.epam.dto.request.create.MakeAnOrdersRequestDto;
import com.epam.dto.request.update.UpdateOrderRequestDTO;
import com.epam.entity.Order;
import com.epam.mapper.Mapper;
import com.epam.model.OrderModel;
import com.epam.principal.UserPrincipal;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public OrderAssembler() {
        super(OrderController.class, OrderModel.class);
    }

    @Override
    public OrderModel toModel(Order entity) {
        OrderModel orderModel = mapper.orderToOrderModel(entity);
        orderModel.add(linkTo(
                methodOn(OrderController.class)
                        .removeOrder(new UserPrincipal(), entity.getOrderId()))
                .withSelfRel().withName("remove"));
        orderModel.add(linkTo(
                methodOn(OrderController.class)
                        .updateOrder(new UpdateOrderRequestDTO(), new UserPrincipal()))
                .withSelfRel().withName("update"));
        orderModel.add(linkTo(
                methodOn(OrderController.class)
                        .getOrder(new UserPrincipal(), entity.getOrderId()))
                .withSelfRel().withName("get order"));
        orderModel.add(linkTo(
                methodOn(BookController.class)
                        .getBook(entity.getBookId(), new UserPrincipal()))
                .withSelfRel().withName("get book"));
        return orderModel;
    }

    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends Order> entities, UserPrincipal principal) {
        CollectionModel<OrderModel> orderModels = super.toCollectionModel(entities);
        if (principal != null && principal.getRole().equals("ADMIN")) {
            orderModels.add(linkTo(methodOn(OrderController.class).getAllUserOrders(0, 10, 0,
                    new UserPrincipal())).withSelfRel().withName("get all users orders"));
            orderModels.add(linkTo(methodOn(OrderController.class).removeUserOrder(0, 0)).
                    withSelfRel().withName("remove some user order"));
            orderModels.add(linkTo(methodOn(OrderController.class).getUserOrder(0, 0)).withSelfRel().
                    withName("get some user order"));
            orderModels.add(linkTo(methodOn(OrderController.class).updateUserOrder(new UpdateOrderRequestDTO(),
                    0)).withSelfRel().withName("update some user order"));

        }
        orderModels.add(linkTo(methodOn(OrderController.class).makeAnOrder(new MakeAnOrdersRequestDto(),
                new UserPrincipal())).withSelfRel().withName("create"));
        orderModels.add(linkTo(methodOn(OrderController.class).getAllOrders(new UserPrincipal(), 10, 0)).
                withSelfRel().withName("get all"));
        return orderModels;
    }


}
