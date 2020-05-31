package com.api.demo.grid.utils;

public class AuctionJson {

    public static String addAuctionJson (String auctioneer, String gameKey, String endDate, double price) {

        return "{\n" +
                "\"auctioneer\": \"" + auctioneer + "\",\n" +
                "\"gameKey\": \"" + gameKey + "\",\n" +
                "\"endDate\": \"" + endDate + "\",\n" +
                "\"price\": " + price + "\n" +
                "}";
    }

    public static String addAuctionJson (String auctioneer, String gameKey, String endDate) {

        return "{\n" +
                "\"auctioneer\": \"" + auctioneer + "\",\n" +
                "\"gameKey\": \"" + gameKey + "\",\n" +
                "\"endDate\": \"" + endDate + "\"\n" +
                "}";
    }
}
