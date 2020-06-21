package com.epam.rest.dao;

import com.epam.daos.dao.GenreDAO;
import com.epam.models.entity.Genre;
import com.epam.rest.config.TestConfig;
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
public class GenreDaoTest {
    @InjectMocks
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;


    @Autowired
    private GenreDAO genreDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/dao/genre-table.sql"));
        tables.addScript(new ClassPathResource("/dao/genre-data.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "genre");
    }

    @Test
    public void getGenreListTest() {
        List<Genre> genreList = genreDao.getGenreList().get();
        Assert.assertEquals(2, genreList.size());
    }

    @Test
    public void getGenreByNameTest() {
        Genre genre = genreDao.getGenreByName("horror");
        Assert.assertEquals(1, genre.getGenreId());
    }

    @Test
    public void removeGenreTest() {
        genreDao.removeGenre(1);
        Genre genre = genreDao.getGenreByName("horror");
        Assert.assertEquals(0, genre.getGenreId());
    }

    @Test
    public void createGenreTest() {
        genreDao.createGenreAndReturnId("detective");
        Genre genre = genreDao.getGenreByName("detective");
        Assert.assertEquals(3, genre.getGenreId());
    }

}
