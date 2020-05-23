package com.api.demo.grid.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Set;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
@SuppressFBWarnings
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


    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
}
