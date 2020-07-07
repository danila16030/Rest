package com.epam.service;

import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.request.update.UpdateUserDTO;
import com.epam.entity.User;

import java.util.List;

public interface UserService {

    User getUser(long userId);

    User createUser(CreateUserDTO user);

    List<User> getAll(int limit, int offset);

    void removeUser(long userId);

    User updateUser(UpdateUserDTO updateUserDTO);

    User getTopUser();

}
