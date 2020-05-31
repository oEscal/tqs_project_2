package com.api.demo.grid.controller;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.exception.ForbiddenException;
import com.api.demo.grid.models.User;
import com.api.demo.grid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;


@RestController
@RequestMapping("/grid")
@CrossOrigin
public class Account {

    @Autowired
    private UserService mUserService;


    @PostMapping("/sign-up")
    public User createUser(@Valid @RequestBody UserDTO user) throws ExceptionDetails {

        return mUserService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestHeader("Authorization") String auth) {

        String value = ControllerUtils.getUserFromAuth(auth);
        User user = mUserService.getUser(value);

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/remove-user")
    public void removeUser(@RequestHeader("Authorization") String auth, @RequestParam String username)
            throws ForbiddenException {

        // verify if the user requesting is the same as the buyer
        String usernameRequest = ControllerUtils.getUserFromAuth(auth);
        if (!Objects.equals(username, usernameRequest) && !mUserService.getUser(usernameRequest).isAdmin()) {
            throw new ForbiddenException("The user requesting to remove the user account is not an admin and is not " +
                    "the same as the user requested to remove");
        }

        mUserService.deleteUser(username);
    }
}
