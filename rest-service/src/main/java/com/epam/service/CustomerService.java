package com.epam.service;

import com.epam.entity.Customer;

public interface CustomerService {
    Customer getUser(long customerId);

    Customer getTopCustomer();
}
