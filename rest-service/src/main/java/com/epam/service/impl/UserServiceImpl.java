package com.epam.service.impl;

import com.epam.dao.UserDAO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.request.update.UpdateUserDTO;
import com.epam.entity.User;
import com.epam.exception.DuplicatedException;
import com.epam.exception.ForbitenToDelete;
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

    /**
     * Returns an User object by user name
     * The username argument specify user in database
     * <p>
     * This method return user by name. If user doesn't exist throw exception NoSuchElementException
     *
     * @param username specified user name
     * @return the user at the specified name
     * @throws NoSuchElementException
     * @see User
     */
    @Override
    public User getUser(String username) {
        return userDAO.getUser(username);
    }

    /**
     * Returns the created user
     * The user argument must contain information about the user being created.
     * <p>
     * This method return user that was created. If user with this name already exist throw DuplicatedException
     *
     * @param user object that contain information to be used when creating a user
     * @return the user that was created
     * @throws DuplicatedException
     * @see User
     * @see CreateUserDTO
     */
    @Override
    public User createUser(CreateUserDTO user) {
        if (!userValidator.isExistByName(user.getUsername())) {
            return userDAO.createUser(mapper.userDTOtUser(user));
        }
        throw new DuplicatedException("User with this name is already exist");
    }

    /**
     * Returns an list of users.
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database
     * <p>
     * This method return list of users from database
     *
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return list that contain users from database
     * @see User
     */
    @Override
    public List<User> getAll(int limit, int offset) {
        return userDAO.getAll(limit, offset).get();
    }

    /**
     * Remove user from database.
     * The bookId argument specify user in database
     * <p>
     * This method remove specified user from database.
     * If user doesn't exist throw exception NoSuchElementException
     *
     * @param userId specified user id
     * @throws NoSuchElementException
     */
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

    /**
     * Returns the updated user
     * The userDTO argument must contain information about the user being updated.
     * <p>
     * This method return user that was updated.If user doesn't exist throw exception NoSuchElementException.
     * If user with this name already exist throw DuplicatedException
     *
     * @param userDTO object that contain information to be used when updating
     * @return the user that was updated
     * @throws NoSuchElementException
     * @throws DuplicatedException
     * @see User
     * @see UpdateUserDTO
     */
    @Override
    public User updateUser(UpdateUserDTO userDTO) {
        if (userValidator.isExistById(userDTO.getUserId())) {
            return userDAO.updateUser(mapper.userDTOtUser(userDTO));
        }
        throw new NoSuchElementException("User does not exist");
    }


}
