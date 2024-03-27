package com.prac.practicum11.repository;

import com.prac.practicum11.model.Customer;

import java.io.IOException;

public interface IAuthenticationRepository {
    boolean save(Customer customer) throws IOException;

    Customer findByUsername(String username) throws IOException;
}
