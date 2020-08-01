package com.epam.controller;

import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.logIn(request));
    }

    @PostMapping(value = "/singIn")
    public ResponseEntity singIn(@RequestBody @Valid CreateUserRequestDTO userDTO) {
        return ResponseEntity.ok(authenticationService.singIn(userDTO));
    }
}
