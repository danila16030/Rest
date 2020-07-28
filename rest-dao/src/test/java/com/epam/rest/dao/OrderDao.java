package com.epam.rest.dao;

import com.epam.dao.OrderDAO;
import com.epam.entity.Order;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class OrderDao {
    @InjectMocks
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Mock
    private DataSource dataSource;


    @Autowired
    @Mock
    private OrderDAO orderDAO;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() {

        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/dao/order-table.sql"));
        tables.addScript(new ClassPathResource("/dao/order-data.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "public.order");
    }

    @Test
    public void getByIdTest() {
        Order order = orderDAO.getOrder(1);
        Assert.assertEquals(1, order.getOrderId());
    }

}
