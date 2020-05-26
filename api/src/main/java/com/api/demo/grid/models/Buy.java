package com.api.demo.grid.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
import java.util.Objects;


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

    @OneToOne
    @JoinColumn(name = "sell_id")
    @JsonIgnore
    @ToString.Exclude
    private Sell sell;

    @OneToOne
    @ToString.Exclude
    private Auction auction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Transactional
    public void setUser(User user) {
        //prevent endless loop
        if (sameAsFormer(user)) return ;
        //set new user
        this.user = user;

        //set myself into new owner
        if (user!=null) user.addBuy(this);
    }

    private boolean sameAsFormer(User newUser) {
        return user==null? newUser == null : newUser.equals(user);
    }

    public Date getDate() {
        return (date==null)? null:(Date) date.clone();
    }

    public void setDate(Date date) {
        if (date != null) this.date = (Date) date.clone();
    }

    @ToString.Include
    public long getUserId() {
        if (user == null) return -1L;
        return this.user.getId();
    }

    @ToString.Include
    public long getSellId(){
        if (sell == null) return -1L;
        return this.sell.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buy buy = (Buy) o;
        return Objects.equals(date, buy.date)
                && buy.getSellId() == this.getSellId()
                && this.getUserId()==buy.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellId(), getUserId(), date);
    }
}
