package com.api.demo.models;

import javax.persistence.*;
import java.util.Date;


@Entity
public class ReportUser {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )

    private int reportId;

    @OneToOne
    private User author;

    @OneToOne
    private User reported;

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

    public User getReported() {
        return reported;
    }

    public void setReported(User reported) {
        this.reported = reported;
    }

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
}
