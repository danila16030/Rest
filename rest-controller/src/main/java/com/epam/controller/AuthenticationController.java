package com.epam.controller;

import com.epam.details.UserPrincipalDetailsService;
import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.entity.User;
import com.epam.principal.UserPrincipal;
import com.epam.service.UserService;
import com.epam.token.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider provider;
    private final UserService userService;
    @Autowired
    private UserPrincipalDetailsService service;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider provider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO request) {
        String username = request.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
        User user = userService.getUser(username);
        String token = provider.createToken(username, user.getRole());
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/singIn")
    public ResponseEntity singIn(@RequestBody @Valid CreateUserDTO userDTO) {
        String username = userDTO.getUsername();
        authentication(userDTO);
        User user = userService.createUser(userDTO);
        String token = provider.createToken(username, user.getRole());
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    private void authentication(CreateUserDTO userDTO) {
        UserPrincipal user = service.loadUserByUsername(userDTO.getUsername());
        user.setUsername(userDTO.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }


}
