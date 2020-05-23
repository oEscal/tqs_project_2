package com.api.demo.grid.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
@SuppressFBWarnings
public class ReportReviewUser {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    @EqualsAndHashCode.Exclude
    private int reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_user_id")
    @EqualsAndHashCode.Exclude
    private ReviewUser reported;


    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
}
