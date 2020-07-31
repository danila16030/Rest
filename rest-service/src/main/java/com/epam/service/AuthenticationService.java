package com.epam.service;

import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserRequestDTO;

public interface AuthenticationService {
    String logIn(AuthenticationRequestDTO requestDTO);
    String singIn(CreateUserRequestDTO request);
}
