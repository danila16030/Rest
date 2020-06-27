package com.epam.rowMapper;

import com.epam.dao.impl.fields.BookFields;
import com.epam.entity.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        String author = resultSet.getString(BookFields.AUTHOR);
        String description = resultSet.getString(BookFields.DESCRIPTION);
        int numberOfPage = resultSet.getInt(BookFields.NUMBEROFPAGES);
        float price = resultSet.getInt(BookFields.PRICE);
        String title = resultSet.getString(BookFields.TITLE);
        String writingDate = resultSet.getString(BookFields.WRITINGDATE);
        Book book = new Book(author, description, price, writingDate, numberOfPage, title);
        book.setBookId(resultSet.getInt(BookFields.ID));
        return book;
    }
}
