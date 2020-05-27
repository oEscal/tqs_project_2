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
    private Set<ReportReviewGame> reports;
    private long author;
    private long game;
    private Date date;

    public ReviewGamePOJO(String comment, int score, Set<ReportReviewGame> reports, long author, long game,Date date) {
        this.comment = comment;
        this.score = score;
        this.reports = reports;
        this.author = author;
        this.game = game;
        this.date = (date == null) ? new Date() : date;
    }

    public Date getDate() { return (date == null) ? (Date) new Date().clone() : new Date(date.getTime()); }

    public void setDate(Date date){ this.date = (date == null) ? this.date : date; }


}
