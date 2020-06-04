package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;


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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String comment;

    @Min(0)
    @Max(5)
    @Column(nullable = false)
    private int score;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnore
    private Game game;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAuthorId(){
        if (this.author == null) return -1l;
        return this.author.getId();
    }

    public long getGameId(){
        if (this.game == null) return -1l;
        return this.game.getId();
    }

    public String getAuthorUsername(){
        if (this.author == null) return "";
        return this.author.getUsername();
    }

    public String getGameName(){
        if (this.game == null) return "";
        return this.game.getName();
    }
}
