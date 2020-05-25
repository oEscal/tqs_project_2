package com.api.demo.grid.controller;

import com.api.demo.grid.exception.UserNotFoundException;
import com.api.demo.grid.models.User;
import com.api.demo.grid.proxy.UserInfoProxy;
import com.api.demo.grid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/grid")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/public/user-info", params={"username"})
    public ResponseEntity<UserInfoProxy> getUserInfo(@RequestParam("username") String username){
        try{
            return ResponseEntity.ok(userService.getUserInfo(username));
        } catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/private/user-info", params={"username"})
    public ResponseEntity<User> getPrivateUserInfo(@RequestParam("username") String username){
        try{
            return ResponseEntity.ok(userService.getFullUserInfo(username));
        } catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
