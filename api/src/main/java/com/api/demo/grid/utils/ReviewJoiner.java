package com.api.demo.grid.utils;

import com.api.demo.grid.models.Game;
import com.api.demo.grid.models.ReportReviewGame;
import com.api.demo.grid.models.ReportUser;
import com.api.demo.grid.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
public class ReviewJoiner {
    private long id;
    private String comment;
    private int score;
    private Date date;
    private Set<ReportReviewGame> reportsGame;
    private Set<ReportUser> reportsUser;
    private User author;
    private User target;
    private Game game;
    private boolean isGameReview;
    private String fieldComparator;

    public ReviewJoiner(long id, String comment, int score, Date date, Set<ReportUser> reports, User author, User target) {
        this.id = id;
        this.comment = comment;
        this.score = score;
        this.date = date;
        this.reportsUser = reports;
        this.author = author;
        this.target = target;
        this.isGameReview = false;
    }

    public ReviewJoiner(long id, String comment, int score, Date date, Set<ReportReviewGame> reports, User author, Game game) {
        this.id = id;
        this.comment = comment;
        this.score = score;
        this.date = date;
        this.reportsGame = reports;
        this.author = author;
        this.game = game;
        this.isGameReview = true;
    }


}
