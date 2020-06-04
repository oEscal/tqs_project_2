package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.Transient;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.ZoneId;
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
@Transient
@SuppressFBWarnings
public class Auction {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "auctioneer_user_id", nullable = false)
    @JsonIgnore
    private User auctioneer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "auction_buyer_user_id")
    @JsonIgnore
    private User buyer;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_key_id", nullable = false)
    private GameKey gameKey;

    @Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT (CURRENT_DATE)", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date startDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
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

    public String getAuctioneerName() {
        if (auctioneer != null) {
            return auctioneer.getUsername();
        }
        return null;
    }

    public long getAuctioneerId() {
        if (auctioneer != null) {
            return auctioneer.getId();
        }
        return -1;
    }


    @Transactional
    public void setAuctioneer(User auctioneer) {
        if (Objects.equals(this.auctioneer, auctioneer)) return;

        this.auctioneer = auctioneer;

        if (auctioneer != null) {
            auctioneer.addAuctionCreated(this);
        }
    }

    public String getBuyerName() {
        if (buyer != null) {
            return buyer.getUsername();
        }
        return null;
    }

    public long getBuyerId() {
        if (buyer != null) {
            return buyer.getId();
        }
        return -1;
    }

    @Transactional
    public void setBuyer(User buyer) {
        if (Objects.equals(this.buyer, buyer)) return;

        this.buyer = buyer;

        if (buyer != null) {
            buyer.addAuctionBought(this);
        }
    }

    public void setGameKey(GameKey gameKey) {
        if (Objects.equals(this.gameKey, gameKey)) return;

        this.gameKey = gameKey;

        if (gameKey != null) {
            gameKey.setAuction(this);
        }
    }
}
