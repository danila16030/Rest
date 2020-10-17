package com.epam.controller;

import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok().body(authenticationService.logIn(request));
    }

    @PostMapping(value = "/singUp")
    public ResponseEntity singUp(@RequestBody @Valid CreateUserRequestDTO userDTO) {
        return ResponseEntity.ok().body(authenticationService.singUp(userDTO));
    }
}
