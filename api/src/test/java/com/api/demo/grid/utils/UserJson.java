package com.api.demo.grid.utils;

public class UserJson {

    public static String simpleUserJson (String username, String password, String birthDate, String email, String country,
                                        String name) {

        return "{\n" +
                "\"username\": \"" + username + "\",\n" +
                "\"password\": \"" + password + "\",\n" +
                "\"birthDate\": \"" + birthDate + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"country\": \"" + country + "\",\n" +
                "\"name\": \"" + name + "\"\n" +
                "}";
    }

    public static String userCreditCardJson (String username, String password, String birthDate, String email,
                                             String country, String name, String creditCardNumber, String creditCardCSC,
                                             String creditCardOwner, String creditCardExpirationDate) {

        return "{\n" +
                "\"username\": \"" + username + "\",\n" +
                "\"password\": \"" + password + "\",\n" +
                "\"birthDate\": \"" + birthDate + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"country\": \"" + country + "\",\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"creditCardNumber\": \"" + creditCardNumber + "\",\n" +
                "\"creditCardCSC\": \"" + creditCardCSC + "\",\n" +
                "\"creditCardOwner\": \"" + creditCardOwner + "\",\n" +
                "\"creditCardExpirationDate\": \"" + creditCardExpirationDate + "\"\n" +
                "}";
    }
}
