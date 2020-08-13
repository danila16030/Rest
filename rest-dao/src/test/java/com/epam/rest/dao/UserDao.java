package com.epam.rest.dao;

import com.epam.dao.UserDAO;
import com.epam.entity.User;
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
public class UserDao {
    @InjectMocks
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Mock
    private DataSource dataSource;

    @Autowired
    @Mock
    private UserDAO userDAO;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/dao/user-table.sql"));
        tables.addScript(new ClassPathResource("/dao/user-data.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "public.user");
    }

    @Test
    public void getAllTest() {
        List<User> userList = userDAO.getAll(10, 0).get();
        Assert.assertEquals(3, userList.size());
    }

    @Test
    public void getByIdTest() {
        User user = userDAO.getUser(1);
        Assert.assertEquals(1, user.getUserId());
    }

    @Test
    public void getByNameTest() {
        User user = userDAO.getUser("danila");
        Assert.assertEquals("danila", user.getUsername());
    }

    @Test
    public void getByNameWithoutExceptionTest() {
        User user = userDAO.getUserByNameWithoutException("qwettui");
        Assert.assertNull( user);
    }

    @Test
    public void getByIdWithoutExceptionTest() {
        User user = userDAO.getUserByIdWithoutException(10);
        Assert.assertNull( user);
    }
}
