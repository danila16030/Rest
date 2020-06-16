package service;

import dto.BookDTO;
import dto.ParametersDTO;
import entyty.Book;

import java.util.List;

public interface BookService {
    public List<BookDTO> getAllBooks();
    public List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters);
    public List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters);
    boolean removeBook(String bookName);
    int createBook(BookDTO book);
    boolean updateBook(BookDTO book);
    List<BookDTO> filter(ParametersDTO parameters);
    List<BookDTO> getBooksSortedByName();
    List<BookDTO> getBooksSortedByDate();

}
