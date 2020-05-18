package com.api.demo.grid.controller;


import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Base64;


@RestController
public class Account {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sign-up")
    public User createUser(@Valid @RequestBody User user) throws ExceptionDetails {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ExceptionDetails("There is already a user with that name");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestHeader("Authorization") String auth) {

        String base64Credentials = auth.substring("Basic".length()).trim();
        String username = new String(Base64.getDecoder().decode(base64Credentials)).split(":", 2)[0];
        User user = userRepository.findByUsername(username);

        return ResponseEntity.ok().body(user);
    }
}
