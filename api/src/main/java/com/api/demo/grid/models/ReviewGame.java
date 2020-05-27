package com.api.demo.grid.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
@SuppressFBWarnings
@Table(
        uniqueConstraints =
                {@UniqueConstraint(columnNames = {"user_id", "game_id"})}
)
public class ReviewGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String comment;

    @Min(0)
    @Max(5)
    @Column(nullable = false)
    private int score;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @OneToMany
    private Set<ReportReviewGame> reports;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)

    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", nullable = false)

    private Game game;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
