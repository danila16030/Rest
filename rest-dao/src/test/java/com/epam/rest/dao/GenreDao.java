package com.epam.rest.dao;

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

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class GenreDao {

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
    public void getGenreListTest() {
        List<Genre> genreList = genreDao.getGenreList(10, 0).get();
        Assert.assertEquals(2, genreList.size());
    }


    @Test
    public void getGenreByNameTest() {
        Genre genre = genreDao.getGenreByNameWithoutException("ppol");
        Assert.assertNull(genre.getGenreName());
    }

    @Test
    public void getGenreByIdTest() {
        Genre genre = genreDao.getGenreById(1);
        Assert.assertEquals("horror", genre.getGenreName());
    }

    @Test
    public void getGenreByIdWithoutExceptionTest() {
        Genre genre = genreDao.getGenreByIdWithoutException(0);
        Assert.assertNull(genre);
    }


}
