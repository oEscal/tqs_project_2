package com.api.demo.grid.pojos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewUserPOJO {
    private String comment;
    private int score;
    private Date date;
    private long author;
    private long target;

    public ReviewUserPOJO(String comment, int score, long author, long target) {
        this.comment = comment;
        this.score = score;
        this.date = (date == null) ? new Date() : date;
        this.author = author;
        this.target = target;
    }

    public Date getDate() { return (date == null) ? (Date) new Date().clone() : new Date(date.getTime()); }

    public void setDate(Date date){ this.date = (date == null) ? this.date : date; }
}
