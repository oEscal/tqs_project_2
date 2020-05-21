package com.api.demo.grid.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@JsonSerialize
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    /***
     *  User basic info
     ***/
    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean admin;

    private int age;

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

    public User(String name, String email, String password, String username, Date birthDate, UserRole role) {

        this.name = name;
        this.email = email;
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

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getCreditCardCSC() {
        return creditCardCSC;
    }

    public String getCreditCardOwner() {
        return creditCardOwner;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setCreditCardCSC(String creditCardCSC) {
        this.creditCardCSC = creditCardCSC;
    }

    public void setCreditCardOwner(String creditCardOwner) {
        this.creditCardOwner = creditCardOwner;
    }

    public Date getCreditCardExpirationDate() {
        return (Date) creditCardExpirationDate.clone();
    }

    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        this.creditCardExpirationDate = (Date) creditCardExpirationDate.clone();
    }
}
