package com.api.demo.models;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Report {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int reportId;

    @OneToOne
    private User author;

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

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
}
