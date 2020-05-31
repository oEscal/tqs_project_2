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

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

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
        this.birthDate = DATE_FORMAT.format(user.getBirthDate());
        this.startDate = DATE_FORMAT.format(user.getStartDate());
        this.listings = (user.getSells() == null)? new ArrayList<>():new ArrayList<>(user.getSells());
        this.reviews = (user.getReviewUsers() == null)? new ArrayList<>(): new ArrayList<>(user.getReviewUsers());
    }

    public UserInfoProxy(User user, boolean showAllInfo) {

        this(user);
        if (showAllInfo) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.creditCardNumber = user.getCreditCardNumber();
            this.creditCardCSC = user.getCreditCardCSC();
            this.creditCardOwner = user.getCreditCardOwner();
            this.creditCardExpirationDate = (user.getCreditCardExpirationDate() == null) ?
                    null : DATE_FORMAT.format(user.getCreditCardExpirationDate());

        }
    }

    public double getScore(){
        if (reviews.isEmpty()) return -1;
        double sum = 0;
        for (ReviewUser review: reviews) sum += review.getScore();
        return sum/reviews.size();
    }
}
