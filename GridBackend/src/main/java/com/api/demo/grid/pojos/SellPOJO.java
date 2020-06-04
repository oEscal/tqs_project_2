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
    private long userId;
    private double price;
    private Date date = new Date();
    private String gameKey;

    public SellPOJO(String gameKey, long userId, double price, Date date) {
        this.gameKey = gameKey;
        this.userId = userId;
        this.price = price;
        this.date = (date==null)? new Date():date;
    }

    public Date getDate() { return (Date) this.date.clone(); }

    public void setDate(Date date){ this.date = (date==null)? this.date:date; }
}
