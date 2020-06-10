package com.epam.service.impl;

import com.epam.dao.impl.BookDAOImpl;
import com.epam.dto.BookDTO;
import com.epam.entyty.Book;
import com.epam.mapper.BookMapper;
import com.epam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    private BookDAOImpl bookDAO;
    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookDAOImpl dao, BookMapper mapper) {
        this.bookDAO = dao;
        this.bookMapper = mapper;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookDAO.getBookList();
        ArrayList<BookDTO> dtoList = new ArrayList<>();
        for (Book book : bookList) {
            dtoList.add(bookMapper.toDto(book));
        }
        return dtoList;
    }
}
