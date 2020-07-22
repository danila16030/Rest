package com.epam.service.impl;

import com.epam.dao.UserDAO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.request.update.UpdateUserDTO;
import com.epam.entity.User;
import com.epam.exception.ForbitenToDelete;
import com.epam.exception.DuplicatedException;
import com.epam.exception.NoSuchElementException;
import com.epam.mapper.Mapper;
import com.epam.service.UserService;
import com.epam.validator.UserValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private UserValidator userValidator;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    @Autowired
    UserServiceImpl(UserDAO userDAO, UserValidator userValidator) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
    }

    @Override
    public User getUser(long userId) {
        return userDAO.getUser(userId);
    }

    @Override
    public User getUser(String username) {
        return userDAO.getUser(username);
    }

    @Override
    public User createUser(CreateUserDTO user) {
        if (!userValidator.isExistByName(user.getUsername())) {
            return userDAO.createUser(mapper.userDTOtUser(user));
        }
        throw new DuplicatedException("User with this name is already exist");
    }

    @Override
    public List<User> getAll(int limit, int offset) {
        return userDAO.getAll(limit, offset).get();
    }

    @Override
    public void removeUser(long userId) {
        if (userValidator.isExistById(userId)) {
            if (userValidator.canBeRemoved(userId)) {
                userDAO.removeUser(userId);
                return;
            }
            throw new ForbitenToDelete("User still has orders");
        }
        throw new NoSuchElementException("User does not exist");
    }

    @Override
    public User updateUser(UpdateUserDTO userDTO) {
        if (userValidator.isExistById(userDTO.getUserId())) {
            return userDAO.updateUser(mapper.userDTOtUser(userDTO));
        }
        throw new NoSuchElementException("User does not exist");
    }


}
