package com.api.demo.grid.controller;


import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class Account {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sign-up")
    public User createUser(@Valid @RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            // throw new ErrorDetails("There is already a user with that email");
            System.out.println("Ola");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
