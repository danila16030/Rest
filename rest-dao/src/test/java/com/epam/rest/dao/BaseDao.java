package com.epam.rest.dao;

import com.epam.dao.BaseDAO;
import com.epam.dao.GenreDAO;
import com.epam.entity.Genre;
import com.epam.rest.config.Config;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class BaseDao {
    @Autowired
    @Mock
    private BaseDAO<Genre> baseDAO;
    @Autowired
    @Mock
    private GenreDAO genreDao;


    @Autowired
    protected EntityManager entityManager;

    @Before
    public void setUp() throws IOException {
        entityManager.getTransaction().begin();
        String data = new String(Files.readAllBytes(Paths.get("..\\rest-dao\\src\\test\\resources\\dao\\genre-table.sql")));
        entityManager.createNativeQuery(data).executeUpdate();
        data = new String(Files.readAllBytes(Paths.get("..\\rest-dao\\src\\test\\resources\\dao\\genre-data.sql")));
        entityManager.createNativeQuery(data).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @After
    public void tearDown() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DROP TABLE genre").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    @Transactional
    public void createTest() {
        baseDAO.create(new Genre("s"));
        Genre genre = genreDao.getGenreById(3);
        Assert.assertEquals("s", genre.getGenreName());
        entityManager.getTransaction().commit();
    }

    @Test
    @Transactional
    public void removeTest() {
        baseDAO.remove(1, Genre.class);
        Genre genre = genreDao.getGenreByIdWithoutException(1);
        Assert.assertNull(genre);
        entityManager.getTransaction().commit();
    }

    @Test
    @Transactional
    public void updateTest() {
        baseDAO.update(new Genre("as", 2));
        Genre genre = genreDao.getGenreById(2);
        Assert.assertEquals("as", genre.getGenreName());
        entityManager.getTransaction().commit();
    }

}
