package com.epam.mapper;

import com.epam.entity.*;
import com.epam.model.*;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    GenreModel genreToGenreModel(Genre genre);

    BookModel bookToBookModel(Book book);

    OrderModel orderToOrderModel(Order order);

    UserModel userToUserModel(User user);

    List<OrderModel> orderToOrderModel(List<Order> orders);

    CustomerModel customerToCustomerModel(Customer customer);

    Customer userToCustomer(User user);
}
