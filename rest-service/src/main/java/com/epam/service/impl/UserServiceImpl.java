package com.epam.service.impl;

import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dao.UserDAO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.request.update.UpdateUserDTO;
import com.epam.entity.User;
import com.epam.exception.CantBeRemovedException;
import com.epam.exception.DuplicatedException;
import com.epam.exception.NoSuchElementException;
import com.epam.service.UserService;
import com.epam.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private UserValidator userValidator;

    @Autowired
    UserServiceImpl(UserDAO userDAO, OrderUserDAO orderUserDAO, UserValidator userValidator, BookDAO bookDAO,
                    BookGenreDAO bookGenreDAO) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
    }

    @Override
    public User getUser(long userId) {
        User response = userDAO.getUser(userId);
        return response;
    }

    @Override
    public User getUser(String username) {
        User response = userDAO.getUser(username);
        return response;
    }

    @Override
    public User createUser(CreateUserDTO user) {
        if (!userValidator.isExistByName(user.getUsername())) {
            return userDAO.createUser(user.getUsername(), user.getPassword());
        }
        throw new DuplicatedException("User with this name is already exist");
    }

    @Override
    public List<User> getAll(int limit, int offset) {
        List<User> response = userDAO.getAll(limit, offset).get();
        return response;
    }

    @Override
    public void removeUser(long userId) {
        if (userValidator.isExistById(userId)) {
            if (userValidator.canBeRemoved(userId)) {
                userDAO.removeUser(userId);
                return;
            }
            throw new CantBeRemovedException("User still has orders");
        }
        throw new NoSuchElementException("User does not exist");
    }

    @Override
    public User updateUser(UpdateUserDTO updateUserDTO) {
        if (userValidator.isExistById(updateUserDTO.getUserId())) {
            return userDAO.updateUser(updateUserDTO.getUsername(), updateUserDTO.getUserId(), updateUserDTO.getPassword());
        }
        throw new NoSuchElementException("User does not exist");
    }


}
