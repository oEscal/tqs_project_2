package com.api.demo.grid.service;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.models.User;
import org.modelmapper.ModelMapper;
import com.api.demo.grid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserService {

    @Autowired
    private UserRepository mRepository;

    @Autowired
    private ModelMapper mModelMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;


    public User getUser(String username) {
        User user = mRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public User saveUser(UserDTO user) throws ExceptionDetails {

        // verify if there are already an user with that username in the database
        if (this.getUser(user.getUsername()) != null)
            throw new ExceptionDetails("There is already a user with that username");

        // verify if there are already some user with that email in the database
        if (mRepository.findByEmail(user.getEmail()) != null)
            throw new ExceptionDetails("There is already a user with that email");

        // verify if the all credit card info was added at the same time
        String creditCardNumber = user.getCreditCardNumber();
        String creditCardCSC = user.getCreditCardCSC();
        String creditCardOwner = user.getCreditCardOwner();
        Date creditCardExpirationDate = user.getCreditCardExpirationDate();
        boolean noneCreditCardInfo = creditCardNumber == null && creditCardCSC == null  && creditCardOwner == null
                && creditCardExpirationDate == null;
        boolean allCreditCardInfo = creditCardNumber != null && creditCardCSC != null  && creditCardOwner != null
                && creditCardExpirationDate != null;
        if (!allCreditCardInfo && !noneCreditCardInfo)
            throw new ExceptionDetails("If you add a new card you have to give all the details referring to that card");

        User userSave = convertToEntity(user);

        userSave.setPassword(passwordEncoder.encode(userSave.getPassword()));
        User userSaved = mRepository.save(userSave);
        userSaved.setPassword(null);
        return userSaved;
    }

    private User convertToEntity(UserDTO userDto) {
        return mModelMapper.map(userDto, User.class);
    }
}
