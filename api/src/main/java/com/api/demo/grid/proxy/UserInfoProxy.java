package com.api.demo.grid.proxy;

import com.api.demo.grid.models.ReviewUser;
import com.api.demo.grid.models.Sell;
import com.api.demo.grid.models.User;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
public class UserInfoProxy {
    private String username;
    private String name;
    private double score;
    private String photoUrl;
    private String country;
    private String description;
    private String birthDate;
    private String startDate;
    private List<Sell> listings;
    private List<ReviewUser> reviews;

    public UserInfoProxy(User user){
        this.username = user.getUsername();
        this.name = user.getName();
        this.photoUrl = user.getPhotoUrl();
        this.country = user.getCountry();
        this.description = user.getDescription();
        this.birthDate = new SimpleDateFormat("dd/MM/yyyy").format(user.getBirthDate());
        this.startDate = new SimpleDateFormat("dd/MM/yyyy").format(user.getStartDate());
        this.listings = (user.getSells() == null)? new ArrayList<>():new ArrayList<>(user.getSells());
        this.reviews = (user.getReviews() == null)? new ArrayList<>(): new ArrayList<>(user.getReviews());
    }

    private double getScore(){
        if (reviews.isEmpty()) return -1;
        double sum = 0;
        for (ReviewUser review: reviews) sum += review.getScore();
        return sum/reviews.size();
    }
}
