package com.api.demo.grid.service;


import com.api.demo.grid.dtos.UserDTO;
import com.api.demo.grid.exception.ExceptionDetails;
import com.api.demo.grid.exception.UserNotFoundException;
import com.api.demo.grid.models.User;
import com.api.demo.grid.pojos.UserUpdatePOJO;
import com.api.demo.grid.proxy.UserInfoProxy;
import org.modelmapper.ModelMapper;
import com.api.demo.grid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository mRepository;

    @Autowired
    private ModelMapper mModelMapper;

    private BCryptPasswordEncoder mPasswordEncoder = new BCryptPasswordEncoder();


    public User getUser(String username) {
        User user = mRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public User saveUser(UserDTO user) throws ExceptionDetails {

        // verify if there are already an user with that username in the database
        if (this.getUser(user.getUsername()) != null) {
            throw new ExceptionDetails("There is already a user with that username");
        }

        // verify if there are already some user with that email in the database
        if (mRepository.findByEmail(user.getEmail()) != null) {
            throw new ExceptionDetails("There is already a user with that email");
        }

        // verify if the all credit card info was added at the same time
        validateCreditCardInfo(user.getCreditCardNumber(), user.getCreditCardCSC(), user.getCreditCardOwner(),
                user.getCreditCardExpirationDate());

        User userSave = convertToEntity(user);

        userSave.setPassword(mPasswordEncoder.encode(userSave.getPassword()));
        User userSaved = mRepository.save(userSave);
        //userSaved.setPassword(null);
        return userSaved;
    }

    public UserInfoProxy getUserInfo(String username) throws UserNotFoundException {
        User user = mRepository.findByUsername(username);

        if (user == null) throw new UserNotFoundException("Username not found in the database");

        return new UserInfoProxy(user);
    }

    public User getFullUserInfo(String username) throws UserNotFoundException {
        User user = mRepository.findByUsername(username);

        if (user == null) throw new UserNotFoundException("Username not found in the database");

        return user;
    }

    public User updateUser(long id, UserUpdatePOJO userUpdatePOJO)
            throws UserNotFoundException, ExceptionDetails{
        Optional<User> opUser = mRepository.findById(id);
        if (opUser.isEmpty()) throw new UserNotFoundException("Username not found in the database");
        User user = opUser.get();
        //Update name
        if (userUpdatePOJO.getName() != null) user.setName(userUpdatePOJO.getName());

        //Update description
        if (userUpdatePOJO.getDescription() != null) user.setDescription(userUpdatePOJO.getDescription());

        //Update country
        if (userUpdatePOJO.getCountry() != null) user.setCountry(userUpdatePOJO.getCountry());

        //Update password
        if (userUpdatePOJO.getPassword() != null) {
            user.setPassword(mPasswordEncoder.encode(userUpdatePOJO.getPassword()));
        }

        //Update email
        if (userUpdatePOJO.getEmail() != null) {
            if (mRepository.findByEmail(user.getEmail()) != null) {
                throw new ExceptionDetails("There is already a user with that email");
            }
            user.setEmail(userUpdatePOJO.getEmail());
        }

        //Update birthdate
        if (userUpdatePOJO.getBirthDate() != null) user.setBirthDate(userUpdatePOJO.getBirthDate());

        //Update credit card info
        boolean hasCredit = validateCreditCardInfo(
                userUpdatePOJO.getCreditCardNumber(),
                userUpdatePOJO.getCreditCardCSC(),
                userUpdatePOJO.getCreditCardOwner(),
                userUpdatePOJO.getCreditCardExpirationDate());

        if (hasCredit){
            user.setCreditCardNumber(userUpdatePOJO.getCreditCardNumber());
            user.setCreditCardCSC(userUpdatePOJO.getCreditCardCSC());
            user.setCreditCardOwner(userUpdatePOJO.getCreditCardOwner());
            user.setCreditCardExpirationDate(userUpdatePOJO.getCreditCardExpirationDate());
        }

        mRepository.save(user);

        return user;
    }

    private boolean validateCreditCardInfo(String creditCardNumber2, String creditCardCSC2, String creditCardOwner2, Date creditCardExpirationDate2)
            throws ExceptionDetails {
        String creditCardNumber = creditCardNumber2;
        String creditCardCSC = creditCardCSC2;
        String creditCardOwner = creditCardOwner2;
        Date creditCardExpirationDate = creditCardExpirationDate2;
        boolean noneCreditCardInfo = creditCardNumber == null && creditCardCSC == null  && creditCardOwner == null
                && creditCardExpirationDate == null;
        boolean allCreditCardInfo = creditCardNumber != null && creditCardCSC != null  && creditCardOwner != null
                && creditCardExpirationDate != null;
        if (!allCreditCardInfo && !noneCreditCardInfo)
            throw new ExceptionDetails("If you add a new card you have to give all the details referring to that card");
        return allCreditCardInfo;
    }

    private User convertToEntity(UserDTO userDto) {
        return mModelMapper.map(userDto, User.class);
    }
}
