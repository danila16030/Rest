package com.epam.dao.impl;

import com.epam.dao.UserDAO;
import com.epam.dao.impl.fields.UserFields;
import com.epam.entity.User;
import com.epam.exception.NoSuchElementException;
import com.epam.rowMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAOImpl implements UserDAO {
    private JdbcTemplate jdbcTemplate;
    private static final String getUser = "SELECT * FROM public.user WHERE user_id=?";
    private static final String getUserWithoutException = "SELECT * FROM public.user WHERE user_name=? ";
    private static final String getAll = "SELECT * FROM public.user LIMIT ? OFFSET ?";
    private static final String create = "INSERT INTO public.user (username) VALUES(?)";
    private static final String removeUser = "DELETE FROM public.user WHERE user_id = ?";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUser(long userId) {
        try {
            return jdbcTemplate.queryForObject(getUser, new Object[]{userId}, new UserMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public User getUserByNameWithoutException(String username) {
        try {
            return jdbcTemplate.queryForObject(getUserWithoutException, new Object[]{username}, new UserMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            return new User();
        }
    }

    @Override
    public User getUserByIdWithoutException(long userId) {
        try {
            return jdbcTemplate.queryForObject(getUser, new Object[]{userId}, new UserMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            return new User();
        }
    }


    @Override
    public List<User> getAll(int limit, int offset) {
        try {
            return jdbcTemplate.query(getAll, new Object[]{limit, offset}, new UserMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public User createUser(String username) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            return statement;
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        long id = Long.parseLong(String.valueOf(keys.get(UserFields.ID)));
        return new User(username, id);
    }

    @Override
    public void removeUser(long userId) {
        jdbcTemplate.update(removeUser, userId);
    }
}
