package com.api.demo.grid.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
@SuppressFBWarnings
public class Buy {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "sell_id")
    @EqualsAndHashCode.Exclude
    private Sell sell;

    @OneToOne
    @EqualsAndHashCode.Exclude
    private Auction auction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;


    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        if (date != null) this.date = (Date) date.clone();
    }

    public long getUserId() {
        if (user == null) return -1L;
        return this.user.getId();
    }
}
