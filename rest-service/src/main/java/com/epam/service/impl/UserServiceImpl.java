package com.epam.service.impl;

import com.epam.dao.OrderUserDAO;
import com.epam.dao.UserDAO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.responce.CustomerResponseDTO;
import com.epam.dto.responce.OrderResponseDTO;
import com.epam.dto.responce.UserResponseDTO;
import com.epam.exception.CantBeRemovedException;
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
    private OrderUserDAO orderUserDAO;
    private UserValidator userValidator;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    @Autowired
    UserServiceImpl(UserDAO userDAO, OrderUserDAO orderUserDAO, UserValidator userValidator) {
        this.userDAO = userDAO;
        this.orderUserDAO = orderUserDAO;
        this.userValidator = userValidator;
    }

    @Override
    public UserResponseDTO getUser(long userId) {
        UserResponseDTO response = mapper.userToUserDTO(userDAO.getUser(userId));
        response.setOrders(getOrder(response.getUserId()));
        return response;
    }

    @Override
    public UserResponseDTO createUser(CreateUserDTO user) {
        if (!userValidator.isExistByName(user.getUsername())) {
            return mapper.userToUserDTO(userDAO.createUser(user.getUsername()));
        }
        throw new DuplicatedException("User with this name is already exist");
    }

    @Override
    public List<UserResponseDTO> getAll(int limit, int offset) {
        List<UserResponseDTO> response = mapper.userListToUserDTOList(userDAO.getAll(limit, offset));
        setOrder(response);
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
    public CustomerResponseDTO getTopUser() {
        List<CustomerResponseDTO> response = mapper.userListToCustomerDTOList(orderUserDAO.getTopUser());
        return getTopCustomer(response);
    }

    private void setOrder(List<UserResponseDTO> users) {
        for (UserResponseDTO user : users) {
            user.setOrders(getOrder(user.getUserId()));
        }
    }

    private CustomerResponseDTO getTopCustomer(List<CustomerResponseDTO> customers) {
        CustomerResponseDTO topCustomer = new CustomerResponseDTO();
        float highestAmount = 0;
        for (CustomerResponseDTO customer : customers) {
            if (highestAmount < customer.getTotalPrice()) {
                highestAmount = customer.getTotalPrice();
                topCustomer = customer;
            }
        }
        return topCustomer;
    }

    private List<OrderResponseDTO> getOrder(long userId) {
        return mapper.orderListToOrderDTOList(orderUserDAO.getOrders(userId).get());
    }
}
