package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Objects;


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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auctioneer_user_id", nullable = false)
    private User auctioneer;

    @ManyToOne
    @JoinColumn(name = "auction_buyer_user_id")
    private User buyer;

    @OneToOne(cascade = CascadeType.ALL)
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
        if (endDate != null) {
            return (Date) endDate.clone();
        }
        return null;
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            this.endDate = (Date) endDate.clone();
        } else {
            this.endDate = null;
        }
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

    @Transactional
    public void setAuctioneer(User auctioneer) {
        if (Objects.equals(this.auctioneer, auctioneer)) return;

        this.auctioneer = auctioneer;

        if (auctioneer != null)
            auctioneer.addAuctionCreated(this);
    }

    public void setGameKey(GameKey gameKey) {
        if (Objects.equals(this.gameKey, gameKey)) return;

        this.gameKey = gameKey;

        if (gameKey != null)
            gameKey.setAuction(this);
    }
}
