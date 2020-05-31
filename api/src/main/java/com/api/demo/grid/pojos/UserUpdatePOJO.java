package com.api.demo.grid.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserUpdatePOJO {

    private String name;

    private String password;

    private String country;

    private String email;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date birthDate;

    private String description;

    /***
     *  User's credit card info
     ***/
    private String creditCardNumber;

    private String creditCardCSC;

    private String creditCardOwner;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date creditCardExpirationDate;

    public Date getBirthDate() {
        return (Date) birthDate.clone();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = (Date) birthDate.clone();
    }

    public Date getCreditCardExpirationDate() {
        if (creditCardExpirationDate != null) {
            return (Date) creditCardExpirationDate.clone();
        }
        return null;
    }

    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        this.creditCardExpirationDate = (Date) creditCardExpirationDate.clone();
    }
}
