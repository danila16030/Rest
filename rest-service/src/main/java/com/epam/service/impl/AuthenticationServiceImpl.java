package com.epam.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.epam.dao.UserDAO;
import com.epam.details.UserPrincipalDetailsService;
import com.epam.dto.request.AuthenticationRequestDTO;
import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.entity.User;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider provider;

    private final UserService userService;

    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider provider,
            UserService userService, UserDAO userDAO, UserPrincipalDetailsService service,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.userService = userService;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<Object, Object> logIn(AuthenticationRequestDTO request) {
        String username = request.getUsername();
        authentication(request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
        String token = provider.createToken(username);
        User user = userService.getUser(username);
        String role = user.getRole();
        user.setLogined(true);
        userDAO.updateUser(user);
        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("role", role);
        response.put("token", token);
        return response;
    }

    @Override
    public void logOut(String username) {
        User user = userService.getUser(username);
        authentication(username);
        user.setLogined(false);
        userDAO.updateUser(user);
    }

    @Override
    public Map<Object, Object> singUp(CreateUserRequestDTO request) {
        String password = passwordEncoder.encode(request.getPassword());
        request.setPassword(password);
        authentication(request.getUsername());
        userService.createUser(request);
        String username = request.getUsername();
        String token = provider.createToken(username);
        Map<Object, Object> response = new HashMap<>();
        User user = userService.getUser(username);
        String role = user.getRole();
        user.setLogined(true);
        userDAO.updateUser(user);
        response.put("username", username);
        response.put("role", role);
        response.put("token", token);
        return response;
    }

    private void authentication(String userName) {
        UserPrincipal user = new UserPrincipal(new User());
        user.setUsername(userName);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, "",
                user.getAuthorities());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }

}
