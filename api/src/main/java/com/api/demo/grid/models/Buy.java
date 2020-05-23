package com.api.demo.grid.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
public class Buy {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int id;

    @OneToOne
    @JoinColumn(name = "sell_id")
    @EqualsAndHashCode.Exclude
    private Sell sell;

    @OneToOne
    @EqualsAndHashCode.Exclude
    private Auction auction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;


    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
}
