package com.epam.mapper;

import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.entity.Order;
import com.epam.entity.User;
import com.epam.model.*;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    GenreModel genreToGenreModel(Genre genre);

    BookModel bookToBookModel(Book book);

    OrderModel orderToOrderModel(Order order);

    UserModel userToUserModel(User user);

    List<OrderModel> orderToOrderModel(List<Order> orders);

    CustomerModel userToCustomerModel(User user);
}
