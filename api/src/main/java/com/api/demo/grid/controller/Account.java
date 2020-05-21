package com.api.demo.grid.controller;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.User;
import com.api.demo.grid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin
public class Account {

    @Autowired
    private UserService mUserService;


    @PostMapping("/grid/sign-up")
    public User createUser(@Valid @RequestBody UserDTO user) throws ExceptionDetails {

        return mUserService.saveUser(user);
    }
}
