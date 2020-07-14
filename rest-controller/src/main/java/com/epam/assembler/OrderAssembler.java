package com.epam.assembler;

import com.epam.controller.BookController;
import com.epam.controller.OrderController;
import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.entity.Order;
import com.epam.mapper.Mapper;
import com.epam.model.OrderModel;
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
                        .removeOrder(entity.getUserId(), entity.getOrderId()))
                .withSelfRel());
        orderModel.add(linkTo(
                methodOn(OrderController.class)
                        .getOrder(entity.getUserId(), entity.getOrderId()))
                .withSelfRel());
        orderModel.add(linkTo(
                methodOn(BookController.class)
                        .getBook(entity.getBookId()))
                .withSelfRel());

        return orderModel;
    }

    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends Order> entities, long userId) {
        CollectionModel<OrderModel> orderModels = super.toCollectionModel(entities);

        orderModels.add(linkTo(methodOn(OrderController.class).makeAnOrder(new MakeAnOrderRequestDTO())).withSelfRel());
        orderModels.add(linkTo(methodOn(OrderController.class).getAllOrders(userId, 10, 0)).withSelfRel());
        return orderModels;
    }
}
