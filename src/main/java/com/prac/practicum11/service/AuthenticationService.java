package com.prac.practicum11.service;

import java.io.IOException;

import com.prac.practicum11.model.Customer;
import com.prac.practicum11.repository.IAuthenticationRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authenticationService")
public class AuthenticationService implements IAuthenticationService, UserDetailsService {

    private final IAuthenticationRepository authenticationRepository;

    public AuthenticationService(IAuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public boolean register(Customer customer) throws IOException {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        customer.setPassword(passwordEncoded);
        return authenticationRepository.save(customer);
    }

    @Override
    public boolean login(String username, String password) throws IOException {
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