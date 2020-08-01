package com.epam.service;

import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.dto.request.update.updateUserRequestDTO;
import com.epam.entity.User;
import com.epam.principal.UserPrincipal;

import java.util.List;

public interface UserService {

    User getUser(String username);

    User getUser(long id);

    User createUser(CreateUserRequestDTO user);

    List<User> getAll(int limit, int offset);

    void removeUser(long userId);

    User updateUser(updateUserRequestDTO updateUserRequestDTO, long userId);

    User updateUser(updateUserRequestDTO updateUserRequestDTO, UserPrincipal userPrincipal);


}
