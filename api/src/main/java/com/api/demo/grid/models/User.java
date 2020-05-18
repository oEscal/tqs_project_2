package com.api.demo.grid.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date birthDate;

    private String photoUrl;

    //The games he reviewed
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ReviewGame> reviewGames;

    //The users he reviewed
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_from_user_id")
    private Set<ReviewUser> reviewUsers;

    //The users that reviewd him
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole role;


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
        this.username = username;
        this.birthDate = (Date) birthDate.clone();
        this.role = role;
    }
}
