package com.api.demo.grid.utils;

public class BiddingJson {

    public static String addBiddingJson (String buyer, String gameKey, double price) {

        return "{\n" +
                "\"user\": \"" + buyer + "\",\n" +
                "\"gameKey\": \"" + gameKey + "\",\n" +
                "\"price\": " + price + "\n" +
                "}";
    }
}
