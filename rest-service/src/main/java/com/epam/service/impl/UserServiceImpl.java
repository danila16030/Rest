package com.epam.service.impl;

import java.util.List;

import com.epam.dao.UserDAO;
import com.epam.dto.request.create.CreateUserRequestDTO;
import com.epam.dto.request.update.UpdateUserRequestDTO;
import com.epam.entity.User;
import com.epam.exception.DuplicatedException;
import com.epam.exception.ForbitenToDelete;
import com.epam.exception.NoSuchElementException;
import com.epam.mapper.Mapper;
import com.epam.principal.UserPrincipal;
import com.epam.service.UserService;
import com.epam.validator.UserValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    private UserDAO userDAO;

    private UserValidator userValidator;

    private PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserDAO userDAO, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Returns an User object by username
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
     * Returns an User object by user id
     * The id argument specify user in database
     * <p>
     * This method return user by id. If user doesn't exist throw exception NoSuchElementException
     *
     * @param id specified user id
     * @return the user at the specified name
     * @throws NoSuchElementException
     * @see User
     */
    @Override
    public User getUser(long id) {
        return userDAO.getUser(id);
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
     * @see CreateUserRequestDTO
     */
    @Override
    public User createUser(CreateUserRequestDTO user) {
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
     * Returns an list of users.
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database
     * The userName argument specify part of the name
     * <p>
     * This method return list of users from database
     *
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return list that contain users from database
     * @see User
     */
    @Override
    public List<User> getAllByPartialName(String userName, int limit, int offset) {
        return userDAO.getAllByPartialName(userName, limit, offset).get();
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
     * @see UpdateUserRequestDTO
     */
    @Override
    public User updateUser(UpdateUserRequestDTO userDTO, long userId) {
        if (userValidator.isExistById(userId)) {
            User user = mapper.userDTOtUser(userDTO);
            user.setUserId(userId);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return userDAO.updateUser(user);
        }
        throw new NoSuchElementException("User does not exist");
    }

    @Override
    public User updateUser(UpdateUserRequestDTO userDTO, UserPrincipal userPrincipal) {
        if (userPrincipal != null && userValidator.isExistById(userPrincipal.getUserId())) {
            User user = mapper.userDTOtUser(userDTO);
            user.setUserId(userPrincipal.getUserId());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return userDAO.updateUser(user);
        }
        throw new NoSuchElementException("User does not exist");
    }

}
