package com.epam.mapper;

import com.epam.dto.responce.*;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.entity.Order;
import com.epam.entity.User;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    GenreResponseDTO genreToGenreDTO(Genre genre);

    List<GenreResponseDTO> genreListToGenreDTOList(List<Genre> genres);

    BookResponseDTO bookToBookDTO(Book book);

    List<BookResponseDTO> bookListToBookDTOList(List<Book> genres);

    OrderResponseDTO orderToOrderDTO(Order order);

    List<OrderResponseDTO> orderListToOrderDTOList(List<Order> orders);

    UserResponseDTO userToUserDTO(User user);

    List<UserResponseDTO> userListToUserDTOList(List<User> user);

    List<CustomerResponseDTO> userListToCustomerDTOList(List<User> user);
    CustomerResponseDTO userToCustomerDTO(User user);
}
