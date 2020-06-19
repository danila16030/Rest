package com.epam.daoTest;

import com.epam.config.TestConfig;
import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.daos.dao.impl.fields.BookFields;
import com.epam.entytys.dto.ParametersDTO;
import com.epam.entytys.entyty.Book;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
@ContextConfiguration(classes = {TestConfig.class})
public class BookDao {
    @InjectMocks
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    @Autowired
    private DataSource dataSource;

    @InjectMocks
    @Autowired
    private BookDAOImpl bookDAO;

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
        List<Book> bookList = bookDAO.getAllBooks().get();
        Assert.assertEquals(bookList.size(), 3);
    }

    @Test
    public void getBookBeNameTest() {
        Book book = bookDAO.getBookByName("Башни");
        Assert.assertEquals(book.getTitle(), "Башни");
    }

    @Test
    public void filterTest() {
        ParametersDTO parametersDTO = new ParametersDTO();
        parametersDTO.getParameters().put(BookFields.AUTHOR, "king");
        parametersDTO.getParameters().put(BookFields.PRICE, "28");
        List<Book> bookList = bookDAO.filter(parametersDTO).get();
        Assert.assertEquals(bookList.size(), 2);
    }

    @Test
    public void searchByFullCoincidenceTest() {
        ParametersDTO parametersDTO = new ParametersDTO();
        parametersDTO.getParameters().put(BookFields.AUTHOR, "king");
        List<Book> bookList = bookDAO.searchByFullCoincidence(parametersDTO).get();
        Assert.assertEquals(bookList.size(), 2);
    }

    @Test
    public void searchByPartialCoincidenceTest() {
        ParametersDTO parametersDTO = new ParametersDTO();
        parametersDTO.getParameters().put(BookFields.AUTHOR, "ki");
        List<Book> bookList = bookDAO.searchByPartialCoincidence(parametersDTO).get();
        Assert.assertEquals(bookList.size(), 3);
    }

    @Test
    public void removeBookTest() {
        bookDAO.removeBook("Sianie");
        Assert.assertEquals(bookDAO.getAllBooks().get().size(), 2);
    }

    @Test
    public void updateBookTest() {
        bookDAO.updateBook("NewTitle", "wasya", "16.05.2008", "strashno",
                70,48, "Башни");
        Book book=bookDAO.getBookByName("NewTitle");
        Assert.assertEquals(book.getAuthor(),"wasya");
    }

    @Test
    public void createNewBookTest() {
        bookDAO.createNewBook("Василий", "funny", 997, "16.03.2999",
                70,"Сказки");
        Book book=bookDAO.getBookByName("Сказки");
        Assert.assertEquals(book.getAuthor(),"Василий");
    }



}
