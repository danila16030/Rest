package com.epam.service;

import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.dto.request.update.updateUserRequestDTO;
import com.epam.entity.User;

import java.util.List;

public interface UserService {

    User getUser(String username);

    User createUser(CreateUserRequestDTO user);

    List<User> getAll(int limit, int offset);

    void removeUser(long userId);

    User updateUser(updateUserRequestDTO updateUserRequestDTO);

}
