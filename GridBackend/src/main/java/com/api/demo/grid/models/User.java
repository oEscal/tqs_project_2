package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Transient;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
@Transient
@SuppressFBWarnings
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    /***
     *  User basic info
     ***/
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private String password;

    @Column(name = "birth_date", nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(insertable = false, updatable = false, name = "start_date", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    @JsonFormat(pattern="dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date startDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean admin;

    private String photoUrl;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    /***
     *  User's credit card info
     ***/
    @Length(min = 8, max = 19)
    @Pattern(regexp="^([0-9]*)$")
    private String creditCardNumber;

    @Length(min = 3, max = 4)
    @Pattern(regexp="^([0-9]*)$")
    private String creditCardCSC;

    private String creditCardOwner;

    @Temporal(TemporalType.DATE)
    @Future
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date creditCardExpirationDate;

    /***
     *  User's relations with other entities
     ***/
    //The games he reviewed
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReviewGame> reviewGames = new HashSet<>();

    //The users he reviewed
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_from_user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReviewUser> reviewedUsers = new HashSet<>();

    //The users that reviewed him
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_to_user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReviewUser> reviewUsers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Buy> buys = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "auctioneer_user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Auction> auctionsCreated = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "auction_buyer_user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Auction> auctionsWon = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Sell> sells = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    @EqualsAndHashCode.Exclude
    private Set<Game> wishList;

    @Column(columnDefinition = "FLOAT DEFAULT 0.00")
    private double funds = 0;

    // because lombok doesnt support get and set params of Date type with security (clone)
    public Date getBirthDate() {
        if (birthDate != null) {
            return (Date) birthDate.clone();
        }
        return null;
    }

    public String getBirthDateStr(){
        if (birthDate != null) return new SimpleDateFormat("dd/MM/yyyy").format(birthDate);
        return null;
    }

    public void setBirthDate(Date birthDate) {
        if (birthDate != null) {
            this.birthDate = (Date) birthDate.clone();
        }
    }

    public String getStartDateStr(){
        if (startDate != null) return new SimpleDateFormat("dd/MM/yyyy").format(startDate);
        return null;
    }

    public Date getCreditCardExpirationDate() {
        if (creditCardExpirationDate != null) {
            return (Date) creditCardExpirationDate.clone();
        }
        return null;
    }

    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        if (creditCardExpirationDate != null) {
            this.creditCardExpirationDate = (Date) creditCardExpirationDate.clone();
        }
    }

    public double getFunds() { return this.funds; }

    public void setFunds(double funds) { this.funds = funds; }

    public void payWithFunds(double bill) { this.funds -= bill; }

    @Transactional
    public void addSell(Sell sell) {
        if (this.sells.contains(sell)) return;

        this.sells.add(sell);

        sell.setUser(this);
    }

    @Transactional
    public void addAuctionCreated(Auction auction) {
        if (this.auctionsCreated.contains(auction)) return;

        this.auctionsCreated.add(auction);

        auction.setAuctioneer(this);
    }

    @Transactional
    public void addAuctionBought(Auction auction) {
        if (this.auctionsWon.contains(auction)) return;

        this.auctionsWon.add(auction);

        auction.setBuyer(this);
    }
    
    public void addBuy(Buy aboutToBuy) {
        if (this.buys == null) this.buys = new HashSet<>();

        for (Buy buy: this.buys) {
            if (buy.equals(aboutToBuy)) return;
        }

        this.buys.add(aboutToBuy);
        aboutToBuy.setUser(this);
    }

    public double getScore(){
        if (this.reviewUsers.isEmpty()) return -1;
        double sum = 0;
        for (ReviewUser review: reviewUsers) sum += review.getScore();
        return sum/reviewUsers.size();
    }

    public void removeListing(Sell sell) {
        this.sells.remove(sell);
    }

    public void removeGameKey(String gamerKey) {
        List<Buy> toRemove = new ArrayList<>();
        for (Buy buy: buys){
            if (buy.getGamerKey().equals(gamerKey)) toRemove.add(buy);
        }
        buys.removeAll(toRemove);
    }
}
