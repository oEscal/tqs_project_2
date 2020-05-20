package com.api.demo.grid.controller;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;


@RestController
@CrossOrigin
public class Account {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/grid/sign-up")
    public User createUser(@Valid @RequestBody UserDTO user) throws ExceptionDetails {

        System.out.println("OLA\n\n\n\n\n");

        User userSave = convertToEntity(user);

        System.out.println(userSave.toString());
        if (userRepository.findByUsername(userSave.getUsername()) != null)
            throw new ExceptionDetails("There is already a user with that name");

        System.out.println(userSave.toString());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userSave.setPassword(passwordEncoder.encode(userSave.getPassword()));

        System.out.println(userSave.toString());
        return userRepository.save(userSave);
    }

    @PostMapping("/grid/login")
    public ResponseEntity<User> login(@RequestHeader("Authorization") String auth) {

        String base64Credentials = auth.substring("Basic".length()).trim();
        String username = new String(Base64.getDecoder().decode(base64Credentials)).split(":", 2)[0];
        User user = userRepository.findByUsername(username);

        return ResponseEntity.ok().body(user);
    }

    private User convertToEntity(UserDTO postDto) {

        return modelMapper.map(postDto, User.class);
    }
}
