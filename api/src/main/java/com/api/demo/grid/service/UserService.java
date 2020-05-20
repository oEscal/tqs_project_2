package com.api.demo.grid.service;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.User;
import org.modelmapper.ModelMapper;
import com.api.demo.grid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository mRepository;

    @Autowired
    private ModelMapper mModelMapper;


    public User getUser(String username) {
        return mRepository.findByUsername(username);
    }

    public User saveUser(UserDTO user) throws ExceptionDetails {

        User userSave = convertToEntity(user);

        if (this.getUser(userSave.getUsername()) != null)
            throw new ExceptionDetails("There is already a user with that name");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userSave.setPassword(passwordEncoder.encode(userSave.getPassword()));
        return mRepository.save(userSave);
    }

    private User convertToEntity(UserDTO postDto) {
        return mModelMapper.map(postDto, User.class);
    }
}
