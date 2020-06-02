package com.api.demo.grid.pojos;

import com.api.demo.grid.models.ReportReviewGame;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewGamePOJO {
    private String comment;
    private int score;
    private long author;
    private long game;
    private Date date;

    public ReviewGamePOJO(String comment, int score, long author, long game) {
        this.comment = comment;
        this.score = score;
        this.author = author;
        this.game = game;
        this.date = new Date();
    }

    public Date getDate() { return (date == null) ? (Date) new Date().clone() : new Date(date.getTime()); }

    public void setDate(Date date){ this.date = (date == null) ? this.date : date; }
}
