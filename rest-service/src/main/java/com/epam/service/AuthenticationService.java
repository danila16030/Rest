package com.epam.service;

import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserRequestDTO;

import java.util.Map;

public interface AuthenticationService {
    Map<Object, Object> logIn(AuthenticationRequestDTO requestDTO);
    Map<Object, Object> singIn(CreateUserRequestDTO request);
}
