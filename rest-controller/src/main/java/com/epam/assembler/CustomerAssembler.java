package com.epam.assembler;

import com.epam.controller.CustomerController;
import com.epam.controller.GenreController;
import com.epam.controller.OrderController;
import com.epam.controller.UserController;
import com.epam.entity.Customer;
import com.epam.mapper.Mapper;
import com.epam.model.CustomerModel;
import com.epam.model.OrderModel;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerModel> {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    public CustomerAssembler() {
        super(UserController.class, CustomerModel.class);
    }

    @Override
    public CustomerModel toModel(Customer entity) {
        CustomerModel customerModel = mapper.customerToCustomerModel(entity);
        setOrderLinks(customerModel.getOrders(), customerModel.getUserId());
        customerModel.add(linkTo(
                methodOn(CustomerController.class)
                        .getCustomer(entity.getUserId()))
                .withSelfRel());
        if (entity.getFavoriteGenre() != null) {
            customerModel.setFavoriteGenre(mapper.genreToGenreModel(entity.getFavoriteGenre()));
            customerModel.getFavoriteGenre().add(linkTo(
                    methodOn(GenreController.class)
                            .getGenre(entity.getFavoriteGenre().getGenreId()))
                    .withSelfRel());
        }
        return customerModel;
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
