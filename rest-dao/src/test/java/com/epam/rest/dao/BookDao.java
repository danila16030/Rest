package com.epam.rest.dao;

import com.epam.dao.BookDAO;
import com.epam.entity.Book;
import com.epam.rest.config.Config;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class BookDao {
    @InjectMocks
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Mock
    private DataSource dataSource;

    @Autowired
    @Mock
    private BookDAO bookDAO;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/dao/book-table.sql"));
        tables.addScript(new ClassPathResource("/dao/book-data.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "book");
    }

    @Test
    public void getAllBooksTest() {
        List<Book> bookList = bookDAO.getAllBooks(10, 0).get();
        Assert.assertEquals(3, bookList.size());
    }

    @Test
    public void getBookByIdTest() {
        Book book = bookDAO.getBookById(3);
        Assert.assertEquals("Towers", book.getTitle());
    }

    @Test
    public void searchByFullCoincidenceTest() {
        List<Book> bookList = bookDAO.searchByFullCoincidence("Sianie", 10, 0).get();
        Assert.assertEquals(1, bookList.size());
    }

    @Test
    public void searchByPartialCoincidenceTest() {
        List<Book> bookList = bookDAO.searchByPartialCoincidence("Siani", 10, 0).get();
        Assert.assertEquals(2, bookList.size());
    }
}
