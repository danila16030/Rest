package com.epam.rowMapper;

import com.epam.dao.impl.fields.UserFields;
import com.epam.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString(UserFields.NAME));
        user.setUserId(rs.getLong(UserFields.ID));
        return user;
    }
}
