package com.api.demo.grid.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /***
     *  User basic info
     ***/
    @Column(unique = true)
    private String username;

    private String name;

    private String email;

    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean admin;

    private int age;

    private String photoUrl;

    /***
     *  User's credit card info
     ***/
    private String creditCardNumber;

    private String creditCardCSC;

    private String creditCardOwner;

    private Date creditCardExpirationDate;

    /***
     *  User's relations with other entities
     ***/
    //The games he reviewed
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReviewGame> reviewGames;

    //The users he reviewed
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_from_user_id")
    private Set<ReviewUser> reviewUsers;

    //The users that reviewed him
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_to_user_id")
    private Set<ReviewUser> reviewedUsers;

    //The reviews directed to the users
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReviewUser> reviews;

    //The reports this user has issued on game reviews
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReportReviewGame> reportsOnGameReview;

    //The reports this user has issued on user reviews
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReportReviewUser> reportsOnUserReview;

    //The reports this user has received
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_from_user_id")
    private Set<ReportUser> reportsThisUser;

    //The reports this user has issued on users
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_to_user_id")
    private Set<ReportUser> reportsOnUser;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Buy> buys;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Auction> auctions;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Sell> sells;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Set<Game> wishList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<ReviewUser> getReviewGames() {
        return new HashSet<>(this.reviews);
    }

    public Set<ReportUser> getReports() {
        return new HashSet<>(this.reportsOnUser);
    }

    public String getPhotoUrl() { return photoUrl; }

    public void setPhotoUrl(String photo) { this.photoUrl = photo; }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<ReviewUser> getReviewUsers() { return reviewUsers; }

    public Set<ReviewUser> getReviewedUsers() { return reviewedUsers; }

    public Set<ReviewUser> getReviews() { return reviews; }

    public Set<ReportReviewGame> getReportsOnGameReview() { return reportsOnGameReview; }

    public Set<ReportReviewUser> getReportsOnUserReview() { return reportsOnUserReview; }

    public Set<ReportUser> getReportsThisUser() { return reportsThisUser; }

    public Set<ReportUser> getReportsOnUser() { return reportsOnUser; }

    public Set<Buy> getBuys() { return buys; }

    public Set<Auction> getAuctions() { return auctions; }

    public Set<Sell> getSells() { return sells; }

    public Set<Game> getWishList() { return wishList; }
    
    public Date getCreditCardExpirationDate() {
        return (Date) creditCardExpirationDate.clone();
    }

    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        this.creditCardExpirationDate = (Date) creditCardExpirationDate.clone();
    }
}
