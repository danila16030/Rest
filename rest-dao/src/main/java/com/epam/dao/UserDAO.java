package com.epam.dao;

import com.epam.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO{
    User getUser(long userId);

    User getUser(String username);

    User getUserByNameWithoutException(String username);

    User getUserByIdWithoutException(long userId);

    Optional<List<User>> getAll(int limit, int offset);

    Optional<List<User>> getAllByPartialName(String userName,int limit, int offset);

    User createUser(User user);

    void removeUser(long userId);

    User updateUser(User user);
}
