package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import java.util.Date;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.Set;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
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
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private Date birthDate;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean admin;

    private String photoUrl;

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
    private Date creditCardExpirationDate;

    /***
     *  User's relations with other entities
     ***/
    //The games he reviewed
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReviewGame> reviewGames;

    //The users he reviewed
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_from_user_id")
    @JsonIgnore
    private Set<ReviewUser> reviewUsers;

    //The users that reviewed him
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_to_user_id")
    @JsonIgnore
    private Set<ReviewUser> reviewedUsers;

    //The reviews directed to the users
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReviewUser> reviews;

    //The reports this user has issued on game reviews
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReportReviewGame> reportsOnGameReview;

    //The reports this user has issued on user reviews
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReportReviewUser> reportsOnUserReview;

    //The reports this user has received
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_from_user_id")
    @JsonIgnore
    private Set<ReportUser> reportsThisUser;

    //The reports this user has issued on users
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_to_user_id")
    @JsonIgnore
    private Set<ReportUser> reportsOnUser;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Buy> buys;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Auction> auctions;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Sell> sells;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Set<Game> wishList;


    // because lombok doesnt support get and set params of Date type with security (clone)
    public Date getBirthDate() {
        return (Date) birthDate.clone();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = (Date) birthDate.clone();
    }

    public Date getCreditCardExpirationDate() {
        return (Date) creditCardExpirationDate.clone();
    }

    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        this.creditCardExpirationDate = (Date) creditCardExpirationDate.clone();
    }
}
