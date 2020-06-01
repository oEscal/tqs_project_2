package com.api.demo.grid.controller;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.exception.ForbiddenException;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.UserUpdatePOJO;
import com.api.demo.grid.proxy.UserInfoProxy;
import com.api.demo.grid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@RestController
@RequestMapping("/grid")
@CrossOrigin
public class Account {

    @Autowired
    private UserService mUserService;


    @PostMapping("/sign-up")
    public ResponseEntity<UserInfoProxy> createUser(@Valid @RequestBody UserDTO user) throws ExceptionDetails {

        return ResponseEntity.ok().body(new UserInfoProxy(mUserService.saveUser(user), true));
    }

    @PostMapping("/login")
    public ResponseEntity<UserInfoProxy> login(@RequestHeader("Authorization") String auth) {

        String value = ControllerUtils.getUserFromAuth(auth);

        return ResponseEntity.ok().body(new UserInfoProxy(mUserService.getUser(value), true));
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
