package com.epam.mapper;

import java.util.List;

import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.dto.request.update.UpdateOrderRequestDTO;
import com.epam.dto.request.update.UpdateUserRequestDTO;
import com.epam.entity.Auditable;
import com.epam.entity.Book;
import com.epam.entity.Customer;
import com.epam.entity.Genre;
import com.epam.entity.Order;
import com.epam.entity.User;
import com.epam.model.AuditModel;
import com.epam.model.BookModel;
import com.epam.model.CustomerModel;
import com.epam.model.GenreModel;
import com.epam.model.OrderModel;
import com.epam.model.UserModel;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    GenreModel genreToGenreModel(Genre genre);

    BookModel bookToBookModel(Book book);

    OrderModel orderToOrderModel(Order order);

    UserModel userToUserModel(User user);

    List<OrderModel> orderToOrderModel(List<Order> orders);

    CustomerModel customerToCustomerModel(Customer customer);

    AuditModel auditableToDebugModel(Auditable auditable);

    List<AuditModel> auditableListToDebugModelList(List<Auditable> auditable);

    Customer userToCustomer(User user);

    Book bookDTOtoBook(CreateBookRequestDTO bookDTO);

    Book bookDTOtoBook(UpdateBookRequestDTO bookDTO);

    Genre genreDTOtoGenre(CreateGenreRequestDTO genreDTO);

    Genre genreDTOtoGenre(UpdateGenreRequestDTO genreDTO);

    Order orderDTOtOrder(MakeAnOrderRequestDTO orderDTO);

    Order orderDTOtOrder(UpdateOrderRequestDTO orderDTO);

    User userDTOtUser(CreateUserRequestDTO userDTO);

    User userDTOtUser(UpdateUserRequestDTO userDTO);
}
