package com.lucass.spring.crm.service;

import java.util.List;

import com.lucass.spring.crm.model.Customer;

public interface ICustomerService {
    List<Customer> findAll();
    void addCustomer(Customer customer);
}
