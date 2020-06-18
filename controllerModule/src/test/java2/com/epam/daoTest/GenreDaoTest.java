package com.epam.daoTest;

import com.epam.config.TestConfig;
import com.epam.daos.dao.impl.GenreDAOImpl;
import com.epam.entytys.entyty.Genre;
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

    @InjectMocks
    @Autowired
    private DataSource dataSource;

    @InjectMocks
    @Autowired
    private GenreDAOImpl genreDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/genre-table.sql"));
        tables.addScript(new ClassPathResource("/genre-data.sql"));

        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "genre");
    }

    @Test
    public void getGenreListTest() {
        List<Genre> genreList = genreDao.getGenreList().get();
        Assert.assertEquals(genreList.size(), 2);
    }

    @Test
    public void getGenreByNameTest() {
        Genre genre = genreDao.getGenreByName("horror");
        Assert.assertEquals(genre.getGenreId(), 1);
    }

    @Test
    public void removeGenreTest() {
        genreDao.removeGenre("horror");
        Genre genre = genreDao.getGenreByName("horror");
        Assert.assertEquals(genre.getGenreId(), 0);
    }

    @Test
    public void createGenreTest() {
        genreDao.createGenre("detective");
        Genre genre = genreDao.getGenreByName("detective");
        Assert.assertEquals(genre.getGenreId(), 3);
    }

}
