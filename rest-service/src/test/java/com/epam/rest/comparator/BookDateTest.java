package com.epam.rest.comparator;

import com.epam.models.entity.Book;
import com.epam.services.comparator.BookDateComparator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class BookDateTest {
    @InjectMocks
    @Autowired
    private BookDateComparator bookDateComparator;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void compare() {
        Book book1 = createBook();
        Book book2 = createBook();
        book2.setWritingDate("16.03.1586");
        int result = bookDateComparator.compare(book1, book2);
        assertTrue(result > 0);
    }

    private Book createBook() {
        return new Book("Vasua", "creepy", 985, "16.03.2200", 88,
                "It");
    }
}
