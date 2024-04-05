package com.prac.practicum11.repository;

import com.prac.practicum11.model.Customer;
import org.springframework.data.repository.CrudRepository;

    public interface AuthenticationDBRepository extends CrudRepository<Customer, String> {
    Customer findByUsername(String username);
}
