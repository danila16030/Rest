package com.epam.rest.dao;

import com.epam.dao.OrderUserDAO;
import com.epam.entity.Customer;
import com.epam.entity.Order;
import com.epam.entity.OrderUser;
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
public class OrderUserDao {
    @InjectMocks
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Mock
    private DataSource dataSource;


    @Autowired
    @Mock
    private OrderUserDAO orderUserDAO;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/dao/order-table.sql"));
        tables.addScript(new ClassPathResource("/dao/order-data.sql"));
        tables.addScript(new ClassPathResource("/dao/user-table.sql"));
        tables.addScript(new ClassPathResource("/dao/user-data.sql"));
        tables.addScript(new ClassPathResource("/dao/order_user-table.sql"));
        tables.addScript(new ClassPathResource("/dao/order_user-data.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "public.order", "public.user", "order_user");
    }

    @Test
    public void getOrdersTest() {
        List<Order> orders = orderUserDAO.getOrders(1, 10, 0).get();
        Assert.assertEquals(2, orders.size());
    }

    @Test
    public void checkConnectionTest() {
        Assert.assertTrue(orderUserDAO.checkConnection(new OrderUser(1, 1)));
    }

    @Test
    public void getCustomersTest() {
        List<Customer> customers = orderUserDAO.getCustomers();
        Assert.assertEquals(3, customers.size());
    }

    @Test
    public void getAllOrdersTest() {
        List<Order> orders = orderUserDAO.getOrders(1).get();
        Assert.assertEquals(2, orders.size());
    }
}
