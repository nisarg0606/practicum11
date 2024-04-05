package com.prac.practicum11.service;

import com.prac.practicum11.model.Customer;

public interface IAuthenticationService {
    String register(Customer customer) throws Exception;

    boolean login(String username, String password) throws Exception;
}
