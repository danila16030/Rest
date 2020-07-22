package com.epam.mapper;

import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.dto.request.update.UpdateOrderDTO;
import com.epam.dto.request.update.UpdateUserDTO;
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

    Book bookDTOtoBook(CreateBookRequestDTO bookDTO);

    Book bookDTOtoBook(UpdateBookRequestDTO bookDTO);

    Genre genreDTOtoGenre(CreateGenreRequestDTO genreDTO);

    Genre genreDTOtoGenre(UpdateGenreRequestDTO genreDTO);

    Order orderDTOtOrder(MakeAnOrderRequestDTO orderDTO);

    Order orderDTOtOrder(UpdateOrderDTO orderDTO);

    User userDTOtUser(CreateUserDTO userDTO);

    User userDTOtUser(UpdateUserDTO userDTO);
}
