package com.epam.service;

import java.util.Map;

import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserRequestDTO;

public interface AuthenticationService {

    Map<Object, Object> logIn(AuthenticationRequestDTO requestDTO);

    Map<Object, Object> singUp(CreateUserRequestDTO request);

    void logOut(String userName);
}
