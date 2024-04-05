package com.prac.practicum11.service;

//import java.io.IOException;

import com.prac.practicum11.model.Customer;
import com.prac.practicum11.repository.AuthenticationDBRepository;
//import com.prac.practicum11.repository.IAuthenticationRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authenticationService")
public class AuthenticationService implements IAuthenticationService, UserDetailsService {

    AuthenticationDBRepository authenticationRepository;

    public AuthenticationService(AuthenticationDBRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public String register(Customer customer) {
        // check if the user already exists
        if (authenticationRepository.findByUsername(customer.getUsername()) != null) {
            return "User already exists";
        }
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        customer.setPassword(passwordEncoded);
        try{
            authenticationRepository.save(customer);
            return "User registered successfully";
        } catch (Exception e) {
            return "Error registering user";
        }
    }

    @Override
    public boolean login(String username, String password) {
        Customer customer = authenticationRepository.findByUsername(username);
        if (customer == null) {
            return false;
        }
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        return bc.matches(password, customer.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Customer customer = authenticationRepository.findByUsername(username);
            if (customer == null) {
                throw new UsernameNotFoundException("");
            }
            return User.withUsername(username).password(customer.getPassword()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}