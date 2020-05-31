package com.api.demo.grid.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ControllerUtils {

    private ControllerUtils() {
    }

    public static String getUserFromAuth(String auth){
        String base64Credentials = auth.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);

        final String[] values = credentials.split(":", 2);
        return values[0];
    }
}
