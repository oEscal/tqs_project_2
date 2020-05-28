package com.api.demo.grid.controller;

import com.api.demo.grid.exception.UserNotFoundException;
import com.api.demo.grid.models.User;
import com.api.demo.grid.proxy.UserInfoProxy;
import com.api.demo.grid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/grid")
public class UserInfoController {

    @Autowired
    private UserService mUserService;

    @GetMapping(value="/public/user-info", params={"username"})
    public ResponseEntity<UserInfoProxy> getUserInfo(@RequestParam("username") String username){
        try{
            return ResponseEntity.ok(mUserService.getUserInfo(username));
        } catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/private/user-info", params={"username"})
    public ResponseEntity<User> getPrivateUserInfo(@RequestHeader("Authorization") String auth,
                                                   @RequestParam("username") String username){
        String value = ControllerUtils.getUserFromAuth(auth);
        User user = mUserService.getUser(value);

        if (user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found in the database");
        }
        if (!value.equals(username) && !user.isAdmin()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You are not allowed to see this user's private info");
        }
        try{
            return ResponseEntity.ok(mUserService.getFullUserInfo(username));
        } catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
