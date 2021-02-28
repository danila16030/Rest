package com.epam.service;

import java.util.List;

import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    Book getBook(long bookId);

    List<Book> getAllBooks(int limit, int offset);

    List<Book> getBookByPartialCoincidence(String title, int limit, int offset);

    List<Book> getBookByFullCoincidence(String title, int limit, int offset);

    void removeBook(long bookId) throws InvalidDataException;

    Book createBook(CreateBookRequestDTO book) throws InvalidDataException;

    Book updateBook(UpdateBookRequestDTO book) throws InvalidDataException;

    Genre geTheMostCommonGenre(long fist, long second, long third);

    List<Book> getBooksSortedByAuthor(int limit, int offset);

    List<Book> getResult(String title, int limit, int offset, String type);

    void saveImage(MultipartFile file) throws Exception;
}
