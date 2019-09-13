package com.lucass.spring.crm.repository;

import com.lucass.spring.crm.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  CustomerRepository extends CrudRepository<Customer, Long> {

}
