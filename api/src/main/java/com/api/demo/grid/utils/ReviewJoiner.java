package com.api.demo.grid.utils;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ReviewJoiner {
    private long id;
    private String comment;
    private int score;
    private Date date;
    private User author;
    private User target;
    private Game game;
    private boolean isGameReview;
    private String fieldComparator;

    public ReviewJoiner(long id, String comment, int score, Date date, User author, User target) {
        this.id = id;
        this.comment = comment;
        this.score = score;
        this.date = (date == null) ? new Date() : (Date) date.clone();
        this.author = author;
        this.target = target;
        this.isGameReview = false;
    }

    public ReviewJoiner(long id, String comment, int score, Date date, User author, Game game) {
        this.id = id;
        this.comment = comment;
        this.score = score;
        this.date = (date == null) ? new Date() : (Date) date.clone();
        this.author = author;
        this.game = game;
        this.isGameReview = true;
    }

    public Date getDate() { return (Date) this.date.clone(); }

    public void setDate(Date date){ this.date = (Date) date.clone();}

}
