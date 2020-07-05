package com.epam.service;

import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.responce.BookResponseDTO;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface BookService {

    BookResponseDTO getBook(long bookId);


    BookResponseDTO changeBookPrice(ParametersRequestDTO parameters);

    List<BookResponseDTO> getAllBooks(int limit, int offset);

    List<BookResponseDTO> getBookByPartialCoincidence(ParametersRequestDTO parameters, int limit, int offset)
            throws InvalidDataException;

    List<BookResponseDTO> getBookByFullCoincidence(ParametersRequestDTO parameters, int limit, int offset)
            throws InvalidDataException;

    boolean removeBook(long bookId) throws InvalidDataException;

    BookResponseDTO createBook(CreateBookRequestDTO book) throws InvalidDataException;

    BookResponseDTO updateBook(UpdateBookRequestDTO book) throws InvalidDataException;

    List<BookResponseDTO> filter(ParametersRequestDTO parameters, int limit, int offset) throws InvalidDataException;

    List<BookResponseDTO> getBooksSortedByName(int limit, int offset);

    List<BookResponseDTO> getBooksSortedByDate(int limit, int offset);

}
