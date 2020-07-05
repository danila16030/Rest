package com.epam.service;

import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.responce.CustomerResponseDTO;
import com.epam.dto.responce.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO getUser(long userId);

    UserResponseDTO createUser(CreateUserDTO user);

    List<UserResponseDTO> getAll(int limit, int offset);

    void removeUser(long userId);

    CustomerResponseDTO getTopUser();

}
