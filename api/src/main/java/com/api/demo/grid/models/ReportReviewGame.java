package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class ReportReviewGame {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    @EqualsAndHashCode.Exclude
    private int reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User author;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_game_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private ReviewGame reported;


    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }

    public long getAuthorId(){ return (author == null)? 0:this.author.getId(); }

    public long getTargetId() { return (reported == null)? 0:reported.getId(); }
}
