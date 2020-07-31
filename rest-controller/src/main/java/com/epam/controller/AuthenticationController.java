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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO request) {
        String token = authenticationService.logIn(request);
        String username = request.getUsername();
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/singIn")
    public ResponseEntity singIn(@RequestBody @Valid CreateUserRequestDTO userDTO) {
        String token = authenticationService.singIn(userDTO);
        String username = userDTO.getUsername();
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
