package com.api.demo.grid.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Set;


@Entity
public class ReviewGame {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String comment;

    @Min(0)
    @Min(5)
    private int score;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany
    private Set<ReportReviewGame> reports;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }

    public Set<ReportReviewGame> getReports() { return reports; }

    public User getAuthor() { return author; }

    public Game getGame() { return game; }
}
