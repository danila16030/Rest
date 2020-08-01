package com.epam.service.impl;

import com.epam.details.UserPrincipalDetailsService;
import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.principal.UserPrincipal;
import com.epam.provider.JwtTokenProvider;
import com.epam.service.AuthenticationService;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider provider;
    private final UserService userService;
    private UserPrincipalDetailsService service;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider provider,
                                     UserService userService, UserPrincipalDetailsService service,
                                     PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.userService = userService;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<Object, Object> logIn(AuthenticationRequestDTO request) {
        String username = request.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
        String token = provider.createToken(username);
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return response;
    }

    @Override
    public Map<Object, Object> singIn(CreateUserRequestDTO request) {
        String password = passwordEncoder.encode(request.getPassword());
        request.setPassword(password);
        authentication(request);
        userService.createUser(request);
        String username = request.getUsername();
        String token = provider.createToken(username);
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);
        return response;
    }

    private void authentication(CreateUserRequestDTO userDTO) {
        UserPrincipal user = service.loadUserByUsername(userDTO.getUsername());
        user.setUsername(userDTO.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, "",
                user.getAuthorities());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }

}
