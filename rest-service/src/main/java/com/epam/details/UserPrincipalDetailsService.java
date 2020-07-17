package com.epam.details;

import com.epam.dao.UserDAO;
import com.epam.entity.User;
import com.epam.exception.NoSuchElementException;
import com.epam.principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByNameWithoutException(username);
        if (user == null) {
            throw new NoSuchElementException();
        }
        return new UserPrincipal(user);
    }
}
