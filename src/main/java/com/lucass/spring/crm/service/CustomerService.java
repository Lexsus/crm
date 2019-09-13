package com.lucass.spring.crm.service;

import java.util.List;

import com.lucass.spring.crm.repository.CustomerRepository;
import com.lucass.spring.crm.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService{
    @Autowired
    private CustomerRepository repository;

    @Override public List<Customer> findAll() {
        List<Customer> list = (List<Customer>) repository.findAll();
        return list;
    }

    @Override public void addCustomer(Customer customer) {
        repository.save(customer);
    }
}
