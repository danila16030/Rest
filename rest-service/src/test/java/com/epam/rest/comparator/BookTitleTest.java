package com.epam.rest.comparator;

import com.epam.models.entity.Book;
import com.epam.rest.config.TestConfig;
import com.epam.services.comparator.BookTitleComparator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class BookTitleTest {

    @InjectMocks
    @Autowired
    private BookTitleComparator bookTitleComparator;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void compare() {
        Book book1 = createBook();
        Book book2 = createBook();
        book2.setTitle("anna");
        int result = bookTitleComparator.compare(book1, book2);
        assertTrue(result < 0);
    }

    private Book createBook() {
        return new Book("Vasua", "creepy", 985, "16.03.2200", 88,
                "It");
    }
}
