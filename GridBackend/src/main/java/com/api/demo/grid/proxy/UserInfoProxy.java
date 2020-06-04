package com.api.demo.grid.proxy;

import com.api.demo.grid.models.ReviewUser;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class UserInfoProxy {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private long id;
    private String username;
    private String name;
    private String email;
    private boolean admin;
    private String photoUrl;
    private String country;
    private String description;
    private String birthDate;
    private String startDate;
    private double funds;
    private List<Sell> listings;
    private List<ReviewUser> reviews;

    // card details
    private String creditCardNumber;
    private String creditCardCSC;
    private String creditCardOwner;
    private String creditCardExpirationDate;


    public UserInfoProxy(User user){
        this.admin = user.isAdmin();
        this.username = user.getUsername();
        this.name = user.getName();
        this.photoUrl = user.getPhotoUrl();
        this.country = user.getCountry();
        this.description = user.getDescription();
        this.birthDate = simpleDateFormat.format(user.getBirthDate());
        this.startDate = simpleDateFormat.format(user.getStartDate());
        this.listings = (user.getSells() == null)? new ArrayList<>():new ArrayList<>(user.getSells());
        this.reviews = (user.getReviewUsers() == null)? new ArrayList<>(): new ArrayList<>(user.getReviewUsers());
    }

    public UserInfoProxy(User user, boolean showAllInfo) {

        this(user);
        if (showAllInfo) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.funds = user.getFunds();
            this.creditCardNumber = user.getCreditCardNumber();
            this.creditCardCSC = user.getCreditCardCSC();
            this.creditCardOwner = user.getCreditCardOwner();
            this.creditCardExpirationDate = (user.getCreditCardExpirationDate() == null) ?
                    null : simpleDateFormat.format(user.getCreditCardExpirationDate());

        }
    }

    public double getScore(){
        if (reviews.isEmpty()) return -1;
        double sum = 0;
        for (ReviewUser review: reviews) sum += review.getScore();
        return sum/reviews.size();
    }
}
