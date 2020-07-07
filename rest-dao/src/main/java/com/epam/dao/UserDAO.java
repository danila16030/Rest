package com.epam.dao;

import com.epam.entity.User;

import java.util.List;

public interface UserDAO extends SetData {
    User getUser(long userId);
    User getUserByNameWithoutException(String username);
    User getUserByIdWithoutException(long userId);
    List<User> getAll(int limit,int offset);
    User createUser(String username);
    void removeUser(long userId);
    User updateUser(String username,long userId);
}
