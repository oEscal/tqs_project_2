package com.api.demo.grid.utils;

public class UserJson {

    public static String simplesUserJson(String username, String password, String birthDate, String email, String country,
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
}
