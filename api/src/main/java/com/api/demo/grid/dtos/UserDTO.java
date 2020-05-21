package com.api.demo.grid.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO {

    /***
     *  User basic info
     ***/
    private String username;

    private String name;

    private String email;

    private String country;

    private String password;

    private Date birthDate;

    private boolean admin;

    private String photoUrl;

    /***
     *  User's credit card info
     ***/
    private String creditCardNumber;

    private String creditCardCSC;

    private String creditCardOwner;

    private Date creditCardExpirationDate;


    // because lombok doesnt support get and set params of Date type with security (clone)
    public Date getBirthDate() {
        return (Date) birthDate.clone();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = (Date) birthDate.clone();
    }

    public Date getCreditCardExpirationDate() {
        if (creditCardExpirationDate != null)
            return (Date) creditCardExpirationDate.clone();
        return null;
    }

    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        this.creditCardExpirationDate = (Date) creditCardExpirationDate.clone();
    }


    public UserDTO(String username, String name, String email, String country, String password, Date birthDate) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.country = country;
        this.password = password;
        this.birthDate = (Date) birthDate.clone();
    }
}
