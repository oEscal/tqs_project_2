package com.api.demo.grid.controller;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.User;
import com.api.demo.grid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@RestController
@CrossOrigin
public class Account {

    @Autowired
    private UserService mUserService;


    @PostMapping("/grid/sign-up")
    public User createUser(@Valid @RequestBody UserDTO user) throws ExceptionDetails {

        return mUserService.saveUser(user);
    }

    @PostMapping("/grid/login")
    public ResponseEntity<User> login(@RequestHeader("Authorization") String auth) {

        String base64Credentials = auth.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);

        final String[] values = credentials.split(":", 2);
        User user = mUserService.getUser(values[0]);

        return ResponseEntity.ok().body(user);
    }
}
