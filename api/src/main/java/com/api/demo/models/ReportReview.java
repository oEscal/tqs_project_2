package com.api.demo.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class ReportReview {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )

    private int reportId;

    @OneToOne
    private User author;

    @OneToOne
    private Review reported;

    @Temporal(TemporalType.DATE)
    private Date date;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Review getReported() {
        return reported;
    }

    public void setReported(Review reported) {
        this.reported = reported;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
}
