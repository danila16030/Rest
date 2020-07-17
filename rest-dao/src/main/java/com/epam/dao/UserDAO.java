package com.epam.dao;

import com.epam.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    User getUser(long userId);

    User getUser(String username);

    User getUserByNameWithoutException(String username);

    User getUserByIdWithoutException(long userId);

    Optional<List<User>> getAll(int limit, int offset);

    User createUser(String username, String password);

    void removeUser(long userId);

    User updateUser(String username, long userId, String password);
}
