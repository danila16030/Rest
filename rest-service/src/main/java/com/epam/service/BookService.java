package com.epam.service;

import com.epam.dto.request.CreateBookRequestDTO;
import com.epam.dto.request.UpdateBookRequestDTO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.responce.BookResponseDTO;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface BookService {

    BookResponseDTO getBook(long bookId);

    List<BookResponseDTO> getAllBooks();

    List<BookResponseDTO> getBookByPartialCoincidence(ParametersRequestDTO parameters) throws InvalidDataException;

    List<BookResponseDTO> getBookByFullCoincidence(ParametersRequestDTO parameters) throws InvalidDataException;

    boolean removeBook(long bookId) throws InvalidDataException;

    BookResponseDTO createBook(CreateBookRequestDTO book) throws InvalidDataException;

    BookResponseDTO updateBook(UpdateBookRequestDTO book) throws InvalidDataException;

    List<BookResponseDTO> filter(ParametersRequestDTO parameters) throws InvalidDataException;

    List<BookResponseDTO> getBooksSortedByName();

    List<BookResponseDTO> getBooksSortedByDate();

}
