package com.epam.assembler;

import com.epam.controller.CustomerController;
import com.epam.controller.GenreController;
import com.epam.controller.OrderController;
import com.epam.controller.UserController;
import com.epam.dto.request.update.UpdateOrderRequestDTO;
import com.epam.entity.Customer;
import com.epam.mapper.Mapper;
import com.epam.model.CustomerModel;
import com.epam.model.OrderModel;
import com.epam.principal.UserPrincipal;
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
        if (entity.getFavoriteGenre() != null) {
            customerModel.setFavoriteGenre(mapper.genreToGenreModel(entity.getFavoriteGenre()));
            customerModel.getFavoriteGenre().add(linkTo(
                    methodOn(GenreController.class)
                            .getGenre(entity.getFavoriteGenre().getGenreId(), new UserPrincipal()))
                    .withSelfRel().withName("get"));
        }
        return customerModel;
    }

    public CustomerModel toCustomerModel(Customer entity, UserPrincipal principal) {
        CustomerModel customerModel = toModel(entity);
        if (principal != null && principal.getRole().equals("ADMIN")) {
            customerModel.add(linkTo(
                    methodOn(CustomerController.class)
                            .getCustomer(0,new UserPrincipal()))
                    .withSelfRel().withName("get"));
        }
        if (principal != null) {
            setOrderLinks(customerModel.getOrders());
            customerModel.add(linkTo(
                    methodOn(CustomerController.class)
                            .getCustomer(new UserPrincipal()))
                    .withSelfRel().withName("get"));
        }
        return customerModel;
    }

    private void setOrderLinks(List<OrderModel> orders) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        for (OrderModel order : orders) {
            order.add(linkTo(
                    methodOn(OrderController.class)
                            .removeOrder(new UserPrincipal(), order.getOrderId()))
                    .withSelfRel().withName("remove"));
            order.add(linkTo(
                    methodOn(OrderController.class)
                            .updateOrder(new UpdateOrderRequestDTO(),new UserPrincipal()))
                    .withSelfRel().withName("update"));
            order.add(linkTo(
                    methodOn(OrderController.class)
                            .getOrder(new UserPrincipal(), order.getOrderId()))
                    .withSelfRel().withName("get"));
        }
    }
}
