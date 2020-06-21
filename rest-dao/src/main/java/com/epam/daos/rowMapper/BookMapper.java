package com.epam.daos.rowMapper;

import com.epam.daos.dao.impl.fields.BookFields;
import com.epam.models.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class BookMapper implements RowMapper<Book> {
    @Autowired
    private ModelMapper mapper;

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setAuthor(resultSet.getString(BookFields.AUTHOR));
        book.setDescription(resultSet.getString(BookFields.DESCRIPTION));
        book.setNumberOfPages(resultSet.getInt(BookFields.NUMBEROFPAGES));
        book.setPrice(resultSet.getInt(BookFields.PRICE));
        book.setTitle(resultSet.getString(BookFields.TITLE));
        book.setWritingDate(resultSet.getString(BookFields.WRITINGDATE));
        book.setBookId(resultSet.getInt(BookFields.ID));
        return book;
    }
}
