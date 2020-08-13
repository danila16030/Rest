package com.epam.rest.dao;

import com.epam.dao.BookGenreDAO;
import com.epam.entity.Book;
import com.epam.entity.BookGenre;
import com.epam.entity.Genre;
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
public class BookGenreDao {
    @InjectMocks
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Mock
    private DataSource dataSource;

    @Autowired
    @Mock
    private BookGenreDAO bookGenreDAO;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/dao/genre-table.sql"));
        tables.addScript(new ClassPathResource("/dao/genre-data.sql"));
        tables.addScript(new ClassPathResource("/dao/book-table.sql"));
        tables.addScript(new ClassPathResource("/dao/book-data.sql"));
        tables.addScript(new ClassPathResource("/dao/book_genre-table.sql"));
        tables.addScript(new ClassPathResource("/dao/book_genre-data.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "genre", "book", "book_genre");
    }

    @Test
    public void getAllBooksByGenreTest() {
        List<Book> genreList = bookGenreDAO.getAllBooksByGenre(1, 10, 0).get();
        Assert.assertEquals(2, genreList.size());
    }

    @Test
    public void getAllGenresOnBookTest() {
        List<Genre> genreList = bookGenreDAO.getAllGenresOnBook(1).get();
        Assert.assertEquals(2, genreList.size());
    }

    @Test
    public void checkConnectionTest() {
        Assert.assertTrue(bookGenreDAO.checkConnection(new BookGenre(1, 1)));
    }

}
