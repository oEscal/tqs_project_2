package com.api.demo.grid.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class SellPOJO {
    private String gameKey;
    private String platform;
    private String retailer;
    private long gameId;
    private long userId;
    private double price;
    private Date date;

    public SellPOJO(String gameKey, String platform, String retailer,
                    long gameId, long userId, double price, Date date) {
        this.gameKey = gameKey;
        this.platform = platform;
        this.retailer = retailer;
        this.gameId = gameId;
        this.userId = userId;
        this.price = price;
        this.date = (date==null)? new Date():date;
    }

    public Date getDate() { return (Date) this.date.clone(); }

    public void setDate(Date date){ this.date = (date==null)? this.date:date; }
}
