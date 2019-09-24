package com.lucass.spring.crm.repository;

import java.util.Collection;

import com.lucass.spring.crm.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface  CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT customer FROM Customer customer WHERE customer.name LIKE :name%")
    @Transactional(readOnly = true)
    Collection<Customer> findByName(@Param("name") String name);

    @Query("SELECT customer FROM Customer customer WHERE customer.id =:id")
    @Transactional(readOnly = true)
    Customer findById(@Param("id") Integer id);
}
