package com.api.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Set;


@Entity
public class ReviewUser {

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
    private Set<ReportUser> reports;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_from_user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_to_user_id")
    private User target;


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

    public Set<ReportUser> getReports() { return reports; }

    public User getAuthor() { return author; }

    public User getTarget() { return target; }
}
