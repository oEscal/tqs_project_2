package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;


    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }

    public long getAuthorId(){ return (author == null)? 0:this.author.getId(); }

    public long getGameId() { return (game == null)? 0:game.getId(); }
}
