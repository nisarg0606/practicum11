package com.prac.practicum11.controller;

import com.prac.practicum11.model.Customer;
import com.prac.practicum11.service.IAuthenticationService;
import com.prac.practicum11.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AuthenticationController(IAuthenticationService authenticationService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody Customer customer) {
        try {
            return authenticationService.register(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody Customer customer) {
        try {
            if (authenticationService.login(customer.getUsername(), customer.getPassword())) {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword()));
                return tokenService.generateToken(authentication);
            }
            // return json with error message
            return "Invalid username or password";
        } catch (Exception e) {
            // return the exception message
            return e.getMessage();
        }
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the home page!";
    }
}