package com.epam.service;

import java.util.List;

import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.dto.request.update.UpdateUserRequestDTO;
import com.epam.entity.User;
import com.epam.principal.UserPrincipal;

public interface UserService {

    User getUser(String username);

    User getUser(long id);

    User createUser(CreateUserRequestDTO user);

    List<User> getAll(int limit, int offset);

    List<User> getAllByPartialName(String userName, int limit, int offset);

    void removeUser(long userId);

    User updateUser(UpdateUserRequestDTO updateUserRequestDTO, long userId);

    User updateUser(UpdateUserRequestDTO updateUserRequestDTO, UserPrincipal userPrincipal);

}
