package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
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
public class Auction {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auctioneer_user_id", nullable = false)
    private User auctioneer;

    @ManyToOne
    @JoinColumn(name = "auction_buyer_user_id")
    private User buyer;

    @OneToOne
    @JoinColumn(name = "game_key_id", nullable = false)
    private GameKey gameKey;

    @Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT (CURRENT_DATE)", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @Future
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date endDate;

    @Column(nullable = false)
    @Min(0)
    private double price;


    public Date getEndDate() {
        return (Date) endDate.clone();
    }

    public void setEndDate(Date endDate) {
        this.endDate = (Date) endDate.clone();
    }

    public void setStartDate(Date startDate) {
        if (startDate != null) {
            this.startDate = (Date) startDate.clone();
        } else {
            this.startDate = null;
        }
    }

    public Date getStartDate() {
        if (startDate != null) {
            return (Date) startDate.clone();
        }
        return null;
    }
}
